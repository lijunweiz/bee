package cn.unminded.bee.core.util;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.util.regex.Pattern;

/**
 * @author lijunwei
 */
public class JavaParserUtil {

    private static String extractClassNameWithRegex(String javaContent) {
        // 简化的正则提取
        Pattern classPattern = Pattern.compile(
                "\\b(?:public|protected|private|\\s+)?\\s*" +
                        "(?:abstract\\s+)?\\s*(?:strictfp\\s+)?" +
                        "class\\s+([A-Za-z_$][A-Za-z0-9_$]*)"
        );

        java.util.regex.Matcher matcher = classPattern.matcher(javaContent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String extractClassNameWithJavaParser(String javaContent) {
        JavaParser javaParser = new JavaParser();
        CompilationUnit cu = javaParser.parse(javaContent)
                .getResult()
                .orElseThrow(() -> new IllegalArgumentException("java content parse error"));

        for (TypeDeclaration<?> typeDeclaration : cu.getTypes()) {
            if (typeDeclaration.isClassOrInterfaceDeclaration()) {
                return typeDeclaration.getFullyQualifiedName()
                        .orElseThrow(() -> new IllegalArgumentException("class name not found"));
            }
        }

        throw new IllegalArgumentException("java content not illegal");
    }


}
