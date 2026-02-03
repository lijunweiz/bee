package cn.unminded.bee.core.engine.compiler;

import cn.unminded.bee.core.util.JavaParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.*;
import javax.tools.JavaCompiler.CompilationTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lijunwei
 */
public class DynamicCompiler {
    private static final Logger logger = LoggerFactory.getLogger(DynamicCompiler.class);

    private final JavaCompiler compiler;
    private final StandardJavaFileManager stdFileManager;
    private final DynamicClassLoader classLoader;
    private final DiagnosticCollector<JavaFileObject> diagnostics;
    private final Map<String, MemoryJavaFileObject> compiledClasses;

    // 编译选项
    private List<String> options;

    public DynamicCompiler() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public DynamicCompiler(ClassLoader classLoader) {
        this.compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException(
                    "无法获取Java编译器。请确保运行在JDK环境下（而非JRE）"
            );
        }

        this.diagnostics = new DiagnosticCollector<>();
        this.stdFileManager = compiler.getStandardFileManager(
                diagnostics, null, null
        );
        this.compiledClasses = new ConcurrentHashMap<>();
        this.classLoader = new DynamicClassLoader(classLoader, compiledClasses);

        // 默认编译选项
        this.options = Arrays.asList(
                "-g:lines,vars,source", // 生成完整的调试信息
                "-Xlint:unchecked",     // 启用未检查警告
                "-encoding", "UTF-8",   // 指定编码
                "-proc:none"            // 禁用所有注解处理器
        );
    }

    public Class<?> compile(String sourceCode)
            throws CompilationException {
        String fullClassName = JavaParserUtil.extractClassNameWithJavaParser(sourceCode);
        return this.compile(fullClassName);
    }

    /**
     * 编译Java源码字符串
     * @param fullClassName 完整类名（如：com.example.MyRule）
     * @param sourceCode    源代码
     * @return 编译后的Class对象
     */
    public Class<?> compile(String fullClassName, String sourceCode)
            throws CompilationException {
        // 1. 创建内存中的JavaFileObject
        JavaFileObject sourceFile = new StringJavaFileObject(fullClassName, sourceCode);

        // 2. 准备编译任务
        List<JavaFileObject> sourceFiles = Collections.singletonList(sourceFile);

        // 3. 使用自定义的FileManager来捕获生成的字节码
        MemoryFileManager fileManager = new MemoryFileManager(
                stdFileManager,
                classLoader
        );

        // 4. 执行编译
        CompilationTask task = compiler.getTask(
                null,                // Writer out (null 表示使用System.err)
                fileManager,         // 使用自定义的FileManager
                diagnostics,         // 诊断收集器
                options,             // 编译选项
                null,                // 要处理的注解类（null表示无）
                sourceFiles          // 要编译的源文件
        );

        boolean success = task.call();

        // 5. 处理编译结果
        if (!success) {
            StringBuilder errorMsg = new StringBuilder("编译失败：\n");
            for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                errorMsg.append(formatDiagnostic(diagnostic)).append("\n");
            }
            throw new CompilationException(errorMsg.toString());
        }
        fileManager.register();

        try {
            // 6. 从内存中加载类
            return classLoader.loadClass(fullClassName);
        } catch (ClassNotFoundException e) {
            throw new CompilationException("无法加载编译后的类: " + fullClassName, e);
        }
    }

    /**
     * 编译并立即创建实例
     */
    public <T> T compileAndInstantiate(String sourceCode, Class<T> interfaceType)
            throws CompilationException, IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException {
        String fullClassName = JavaParserUtil.extractClassNameWithJavaParser(sourceCode);
        return this.compileAndInstantiate(fullClassName, sourceCode, interfaceType);
    }

    /**
     * 编译并立即创建实例
     */
    public <T> T compileAndInstantiate(String fullClassName, String sourceCode, Class<T> interfaceType)
            throws CompilationException, IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = compile(fullClassName, sourceCode);
        Object instance = clazz.getDeclaredConstructor().newInstance();

        if (!interfaceType.isAssignableFrom(clazz)) {
            throw new CompilationException(
                    "类 " + fullClassName + " 未实现接口: " + interfaceType.getName()
            );
        }

        return interfaceType.cast(instance);
    }

    /**
     * 设置编译选项
     */
    public void setCompilerOptions(String... options) {
        this.options = Arrays.asList(options);
    }

    /**
     * 获取编译诊断信息
     */
    public List<Diagnostic<? extends JavaFileObject>> getDiagnostics() {
        return diagnostics.getDiagnostics();
    }

    /**
     * 清空已编译的类缓存
     */
    public void clearCache() {
        compiledClasses.clear();
        classLoader.clearCache();
    }

    /**
     * 格式化诊断信息
     */
    private String formatDiagnostic(Diagnostic<? extends JavaFileObject> diagnostic) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(diagnostic.getKind()).append("] ");

        if (diagnostic.getSource() != null) {
            sb.append(diagnostic.getSource().getName());
        }

        if (diagnostic.getLineNumber() > 0) {
            sb.append(" 行: ").append(diagnostic.getLineNumber());
            if (diagnostic.getColumnNumber() > 0) {
                sb.append(", 列: ").append(diagnostic.getColumnNumber());
            }
        }

        sb.append(": ").append(diagnostic.getMessage(null));
        return sb.toString();
    }

    /**
     * 内存中的Java源码对象
     */
    private static class StringJavaFileObject extends SimpleJavaFileObject {
        private final String sourceCode;

        protected StringJavaFileObject(String className, String sourceCode) {
            super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension),
                    Kind.SOURCE);
            this.sourceCode = sourceCode;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return sourceCode;
        }
    }

    /**
     * 内存中的字节码对象
     */
    private static class MemoryJavaFileObject extends SimpleJavaFileObject {
        private final ByteArrayOutputStream byteCode = new ByteArrayOutputStream();;
        private final String className;

        protected MemoryJavaFileObject(String className, Kind kind) {
            super(URI.create("mem:///" + className.replace('.', '/') + kind.extension), kind);
            this.className = className;
        }

        @Override
        public OutputStream openOutputStream() {
            byteCode.reset();
            return byteCode;
        }

        public byte[] getBytes() {
            return byteCode.toByteArray();
        }

        public String getClassName() {
            return className;
        }
    }

    /**
     * 自定义FileManager，将字节码输出到内存
     */
    private static class MemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        private final DynamicClassLoader classLoader;
        private MemoryJavaFileObject fileObject;

        protected MemoryFileManager(JavaFileManager fileManager,
                                    DynamicClassLoader classLoader) {
            super(fileManager);
            this.classLoader = classLoader;
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location,
                                                   String className,
                                                   JavaFileObject.Kind kind,
                                                   FileObject sibling) throws IOException {
            if (kind == JavaFileObject.Kind.CLASS) {
                fileObject = new MemoryJavaFileObject(className, kind);
                return fileObject;
            }
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }

        @Override
        public ClassLoader getClassLoader(Location location) {
            return classLoader;
        }

        public void register() {
            classLoader.registerCompiledClass(fileObject.getClassName(),  fileObject);
        }
    }

    /**
     * 自定义ClassLoader，用于加载内存中的类
     */
    private static class DynamicClassLoader extends ClassLoader {
        private final Map<String, MemoryJavaFileObject> fileObjectMap;
        private final Map<String, Class<?>> classCache;

        public DynamicClassLoader(ClassLoader parent, Map<String, MemoryJavaFileObject> fileObjectMap) {
            super(parent);
            this.fileObjectMap = fileObjectMap;
            this.classCache = new ConcurrentHashMap<>();
        }

        public void registerCompiledClass(String className, MemoryJavaFileObject fileObject) {
            classCache.remove(className); // 清除缓存以便重新加载
            fileObjectMap.put(className, fileObject);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            // 首先检查缓存
            if (classCache.containsKey(name)) {
                return classCache.get(name);
            }

            Class<?> clazz = null;
            try {
                // 再查父类
                clazz = getParent().loadClass(name);
                if (logger.isDebugEnabled()) {
                    logger.debug("load from parent: {}", clazz.getName());
                }
            } catch (ClassNotFoundException e) {
                // 从内存中获取字节码
                byte[] bytecode = fileObjectMap.containsKey(name) ? fileObjectMap.get(name).getBytes() : null;
                if (bytecode == null || bytecode.length == 0) {
                    throw e;
                }
                // 定义类
                clazz = defineClass(name, bytecode, 0, bytecode.length);
                classCache.put(name, clazz);
                if (logger.isDebugEnabled()) {
                    logger.debug("load from cache: {}", clazz.getName());
                }
            }

            return clazz;
        }

        public void clearCache() {
            classCache.clear();
        }
    }

    /**
     * 自定义编译异常
     */
    public static class CompilationException extends Exception {
        public CompilationException(String message) {
            super(message);
        }

        public CompilationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
