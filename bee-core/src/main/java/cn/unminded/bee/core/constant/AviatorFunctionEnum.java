package cn.unminded.bee.core.constant;

import java.util.*;

/**
 * @author lijunwei
 */
public enum AviatorFunctionEnum {
    // 系统函数
    ASSERT_PREDICATE_MSG("assert(predicate, [msg])", "断言函数", "断言函数，当 predicate 的结果为 false 的时候抛出 AssertFailed 异常， msg 错误信息可选"),
    SYSDATE("sysdate()", "系统日期", "返回当前日期对象 java.util.Date"),
    RAND("rand()", "随机数", "返回一个介于 [0, 1) 的随机数，结果为 double 类型"),
    RAND_N("rand(n)", "随机数", "返回一个介于 [0, n) 的随机数，结果为 long 类型"),
    CMP_X_Y("cmp(x, y)", "比较函数", "比较 x 和 y 大小，返回整数，0 表示相等， 1 表达式 x > y，负数则 x < y"),
//    PRINT_OUT_OBJ("print([out],obj)", "打印对象,如果指定 out 输出流，向 out 打印， 默认输出到标准输出"),
//    PRINTLN_OUT_OBJ("println([out],obj)", "与 print 类似,但是在输出后换行"),
//    P_OUT_OBJ("p([out], obj)", "与 print 类似,但是在输出后换行"),
//    PST_OUT_E("pst([out], e)", "等价于 e.printStackTrace()，打印异常堆栈，out 是可选的输出流，默认是标准错误输出"),
    NOW("now()", "当前时间戳", "返回 System.currentTimeMillis() 调用值"),
    LONG_V("long(v)", "长整型转换", "将值转为 long 类型"),
    DOUBLE_V("double(v)", "双精度转换", "将值转为 double 类型"),
    BOOLEAN_V("boolean(v)", "布尔转换", "将值的类型转为 boolean，除了 nil 和 false，其他都值都将转为布尔值 true"),
    STR_V("str(v)", "字符串转换", "将值转为 string 类型，如果是 nil（或者 java null），会转成字符串 'null'"),
    BIGINT_X("bigint(x)", "大整数转换", "将值转为 bigint 类型"),
    DECIMAL_X("decimal(x)", "十进制转换", "将值转为 decimal 类型"),
    IDENTITY_V("identity(v)", "恒等函数", "返回参数 v 自身，用于跟 seq 库的高阶函数配合使用"),
    TYPE_X("type(x)", "类型获取", "返回参数 x 的类型，结果为字符串，如 string, long, double, bigint, decimal, function 等。Java  类则返回完整类名"),
    IS_A_X_CLASS("is_a(x, class)", "类型检查", "当 x 是类 class 的一个实例的时候，返回 true，例如 is_a(\"a\", String) ，class 是类名"),
    IS_DEF_X("is_def(x)", "变量定义检查", "返回变量 x 是否已定义（包括定义为 nil），结果为布尔值"),
//    UNDEF_X("undef(x)", "“遗忘”变量  x，如果变量 x 已经定义，将取消定义"),
    RANGE_START_END_STEP("range(start, end, [step])", "范围创建", "创建一个范围，start 到 end 之间的整数范围，不包括 end， step 指定递增或者递减步幅"),
    TUPLE_X1_X2("tuple(x1, x2, ...)", "元组创建", "创建一个 Object 数组，元素即为传入的参数列表"),
    EVAL_SCRIPT_BINDINGS_CACHED("eval(script, [bindings], [cached])", "脚本求值", "对一段脚本文本 script 进行求值，等价于 AviatorEvaluator.execute(script, env, cached)"),
    COMPARATOR_PRED("comparator(pred)", "比较器转换", "将一个谓词（返回布尔值）转化为 java.util.Comparator 对象，通常用于 sort 函数"),
    MAX_X1_X2_X3("max(x1, x2, x3, ...)", "最大值", "取所有参数中的最大值，比较规则遵循逻辑运算符规则"),
    MIN_X1_X2_X3("min(x1, x2, x3, ...)", "最小值", "取所有参数中的最小值，比较规则遵循逻辑运算符规则"),
//    CONSTANTLY_X("constantly(x)", "用于生成一个函数，它对任意（个数）参数的调用结果 x")

    // 字符串函数
    DATE_TO_STRING_DATE_FORMAT("date_to_string(date,format)", "日期转字符串", "将 Date 对象转化化特定格式的字符串,2.1.1 新增"),
    STRING_TO_DATE_SOURCE_FORMAT("string_to_date(source,format)", "字符串转日期", "将特定格式的字符串转化为 Date 对象,2.1.1 新增"),
    STRING_CONTAINS_S1_S2("string.contains(s1,s2)", "字符串包含", "判断 s1 是否包含 s2,返回 Boolean"),
    STRING_LENGTH_S("string.length(s)", "字符串长度", "求字符串长度,返回 Long"),
    STRING_STARTSWITH_S1_S2("string.startsWith(s1,s2)", "字符串开始判断", "s1 是否以 s2 开始,返回 Boolean"),
    STRING_ENDSWITH_S1_S2("string.endsWith(s1,s2)", "字符串结束判断", "s1 是否以 s2 结尾,返回 Boolean"),
    STRING_SUBSTRING_S_BEGIN_END("string.substring(s,begin[,end])", "子字符串", "截取字符串 s,从 begin 到 end,如果忽略 end 的话,将从 begin 到结尾,与 java.util.String.substring 一样"),
    STRING_INDEXOF_S1_S2("string.indexOf(s1,s2)", "字符串索引", "java 中的 s1.indexOf(s2),求 s2 在 s1 中的起始索引位置,如果不存在为-1"),
    STRING_SPLIT_TARGET_REGEX_LIMIT("string.split(target,regex,[limit])", "字符串分割", "Java 里的 String.split 方法一致"),
    STRING_JOIN_SEQ_SEPERATOR("string.join(seq,seperator)", "字符串连接", "将集合 seq 里的元素以 seperator 为间隔连接起来形成字符串"),
    STRING_REPLACE_FIRST_S_REGEX_REPLACEMENT("string.replace_first(s,regex,replacement)", "字符串替换(首次)", "Java 里的 String.replaceFirst 方法"),
    STRING_REPLACE_ALL_S_REGEX_REPLACEMENT("string.replace_all(s,regex,replacement)", "字符串替换(全部)", "Java 里的 String.replaceAll 方法 "),

    // 数学函数
    MATH_ABS_D("math.abs(d)", "绝对值", "求 d 的绝对值"),
    MATH_ROUND_D("math.round(d)", "四舍五入", "四舍五入"),
    MATH_FLOOR_D("math.floor(d)", "向下取整", "向下取整"),
    MATH_CEIL_D("math.ceil(d)", "向上取整", "向上取整"),
    MATH_SQRT_D("math.sqrt(d)", "平方根", "求 d 的平方根"),
    MATH_POW_D1_D2("math.pow(d1,d2)", "幂运算", "求 d1 的 d2 次方"),
    MATH_LOG_D("math.log(d)", "自然对数", "求 d 的自然对数"),
    MATH_LOG10_D("math.log10(d)", "常用对数", "求 d 以 10 为底的对数"),
    MATH_SIN_D("math.sin(d)", "正弦函数", "正弦函数"),
    MATH_COS_D("math.cos(d)", "余弦函数", "余弦函数"),
    MATH_TAN_D("math.tan(d)", "正切函数", "正切函数"),
    MATH_ATAN_D("math.atan(d)", "反正切函数", "反正切函数"),
    MATH_ACOS_D("math.acos(d)", "反余弦函数", "反余弦函数"),
    MATH_ASIN_D("math.asin(d)", "反正弦函数", "反正弦函数"),

    // 序列和集合函数
    REPEAT_N_X("repeat(n, x)", "重复元素", "返回一个 List，将元素 x 重复 n 次组合而成"),
    REPEATEDLY_N_F("repeatedly(n, f)", "重复调用", "返回一个 List，将函数 f 重复调用 n 次的结果组合而成"),
    SEQ_ARRAY_CLAZZ_E1_E2_E3("seq.array(clazz, e1, e2, e3, ...)", "创建数组", "创建一个指定 clazz 类型的数组，并添加参数 e1,e2,e3...到这个数组并返回。clazz 可以是类似 java.lang.String 的类型，也可以是原生类型，如 int/long/float 等"),
    SEQ_ARRAY_OF_CLAZZ_SIZE1_SIZE2("seq.array_of(clazz, size1, size2, ...sizes)", "创建多维数组", "创建 clazz 类型的一维或多维数组，维度大小为 sizes 指定。clazz 同 seq.array 定义"),
    SEQ_LIST_P1_P2_P3("seq.list(p1, p2, p3, ...)", "创建列表", "创建一个 java.util.ArrayList 实例，添加参数到这个集合并返回"),
    SEQ_SET_P1_P2_P3("seq.set(p1, p2, p3, ...)", "创建集合", "创建一个 java.util.HashSet 实例，添加参数到这个集合并返回"),
    SEQ_MAP_K1_V1_K2_V2("seq.map(k1, v1, k2, v2, ...)", "创建映射", "创建一个 java.util.HashMap 实例，参数要求偶数个，类似 k1,v1 这样成对作为 key-value 存入 map，返回集合"),
    SEQ_ENTRY_KEY_VALUE("seq.entry(key, value)", "创建键值对", "创建 Map.Entry 对象，用于 map, filter 等函数"),
    SEQ_KEYS_M("seq.keys(m)", "获取键集合", "返回 map 的 key 集合"),
    SEQ_VALS_M("seq.vals(m)", "获取值集合", "返回 map 的 value 集合"),
    INTO_TO_SEQ_FROM_SEQ("into(to_seq, from_seq)", "序列转换", "用于 sequence 转换，将 from sequence 的元素使用 seq.add 函数逐一添加到了 to sequence 并返回最终的 to_seq"),
    SEQ_CONTAINS_KEY_MAP_KEY("seq.contains_key(map, key)", "键存在检查", "当 map 中存在 key 的时候（可能为 null），返回 true。对于数组和链表，key 可以是 index，当 index 在有效范围[0..len-1]，返回 true，否则返回 false"),
    SEQ_ADD_COLL_ELEMENT("seq.add(coll, element)", "添加元素", "往集合 coll 添加元素，集合可以是 java.util.Collection"),
    SEQ_ADD_M_KEY_VALUE("seq.add(m, key, value)", "添加键值对", "往集合 m 添加键值对，m 是 java.util.Map"),
    SEQ_ADD_ALL_SEQ1_SEQ2("seq.add_all(seq1, seq2)", "添加所有元素", "将集合 seq2 的元素全部添加到 seq1，5.3.3 版本新增函数"),
    SEQ_PUT_COLL_KEY_VALUE("seq.put(coll, key, value)", "设置元素", "类似 List.set(i, v)。用于设置 seq 在 key 位置的值为 value，seq 可以是 map，数组或者 List。map 就是键值对，数组或者 List 的时候，key 为索引位置整数，value 即为想要放入该索引位置的值"),
    SEQ_REMOVE_COLL_ELEMENT("seq.remove(coll, element)", "删除元素", "从集合或者 hash map 中删除元素或者 key"),
    SEQ_GET_COLL_ELEMENT("seq.get(coll, element)", "获取元素", "从 list、数组或者 hash-map 获取对应的元素值，对于 list 和数组，element 为元素的索引位置（从 0 开始），对于 hash map 来说，element 为 key"),
    MAP_SEQ_FUN("map(seq, fun)", "映射函数", "将函数 fun 作用到集合 seq 每个元素上，返回新元素组成的集合"),
    FILTER_SEQ_PREDICATE("filter(seq, predicate)", "过滤函数", "将谓词 predicate 作用在集合的每个元素上，返回谓词为 true 的元素组成的集合"),
    COUNT_SEQ("count(seq)", "计数函数", "返回集合大小，seq 可以是数组，字符串，range，List 等等"),
    IS_EMPTY_SEQ("is_empty(seq)", "空检查", "等价于 count(seq) == 0，当集合为空或者 nil，返回 true"),
    DISTINCT_SEQ("distinct(seq)", "去重函数", "返回 seq 去重后的结果集合"),
    IS_DISTINCT_SEQ("is_distinct(seq)", "无重复检查", "当 seq 没有重复元素的时候，返回 true，否则返回 false"),
    CONCAT_SEQ1_SEQ2("concat(seq1, seq2)", "连接函数", "将 seq1 和 seq2 连接，返回连接后的结果，复杂度 O(m+n)，m 和 n 分别是两个集合的长度"),
    INCLUDE_SEQ_ELEMENT("include(seq, element)", "包含检查", "判断 element 是否在集合 seq 中，返回 boolean 值，对于 java.util.Set 是 O(1) 时间复杂度，其他为 O(n)"),
    SORT_SEQ_COMPARATOR("sort(seq, [comparator])", "排序函数", "排序集合，仅对数组和 List 有效，返回排序后的新集合，comparator 是一个 java.util.Comparator 实例，可选排序方式"),
    REVERSE_SEQ("reverse(seq)", "反转函数", "将集合元素逆序，返回新的集合"),
    REDUCE_SEQ_FUN_INIT("reduce(seq, fun, init)", "归约函数", "fun 接收两个参数，第一个是集合元素，第二个是累积的函数，本函数用于将 fun 作用在结果值（初始值为 init 指定)和集合的每个元素上面，返回新的结果值；函数返回最终的结果值"),
    TAKE_WHILE_SEQ_PRED("take_while(seq, pred)", "条件选取", "遍历集合 seq，对每个元素调用 pred(x)，返回 true则加入结果集合，最终返回收集的结果集合。也就是说从集合 seq 收集 pred 调用为 true 的元素"),
    DROP_WHILE_SEQ_PRED("drop_while(seq, pred)", "条件丢弃", "与 take_while 相反，丢弃任何 pred(x) 为 true 的元素并返回最终的结果集合"),
    GROUP_BY_SEQ_KEYFN("group_by(seq, keyfn)", "分组函数", "对集合 seq 的元素按照 keyfn(x) 的调用结果做分类，返回最终映射 map。具体使用见文档"),
    ZIPMAP_KEYS_VALUES("zipmap(keys, values)", "键值对映射", "返回一个 HashMap，其中按照 keys 和 values 两个集合的顺序映射键值对。具体使用见文档"),
    SEQ_EVERY_SEQ_FUN("seq.every(seq, fun)", "全匹配函数", "fun 接收集合的每个元素作为唯一参数，返回 true 或 false。当集合里的每个元素调用 fun 后都返回 true 的时候，整个调用结果为 true，否则为 false"),
    SEQ_NOT_ANY_SEQ_FUN("seq.not_any(seq, fun)", "全不匹配函数", "fun 接收集合的每个元素作为唯一参数，返回 true 或 false。当集合里的每个元素调用 fun 后都返回 false 的时候，整个调用结果为 true，否则为 false"),
    SEQ_SOME_SEQ_FUN("seq.some(seq, fun)", "存在匹配函数", "fun 接收集合的每个元素作为唯一参数，返回 true 或 false。当集合里的只要有一个元素调用 fun 后返回 true 的时候，整个调用结果立即为该元素，否则为 nil"),
    SEQ_EQ_VALUE("seq.eq(value)", "等于谓词", "返回一个谓词，用来判断传入的参数是否跟 value 相等，用于 filter 函数，如 filter(seq,seq.eq(3)) 过滤返回等于3的元素组成的集合"),
    SEQ_NEQ_VALUE("seq.neq(value)", "不等于谓词", "与 seq.eq 类似，返回判断不等于的谓词"),
    SEQ_GT_VALUE("seq.gt(value)", "大于谓词", "返回判断大于 value 的谓词"),
    SEQ_GE_VALUE("seq.ge(value)", "大于等于谓词", "返回判断大于等于 value 的谓词"),
    SEQ_LT_VALUE("seq.lt(value)", "小于谓词", "返回判断小于 value 的谓词"),
    SEQ_LE_VALUE("seq.le(value)", "小于等于谓词", "返回判断小于等于 value 的谓词"),
    SEQ_NIL("seq.nil()", "空值谓词", "返回判断是否为 nil 的谓词"),
    SEQ_EXISTS("seq.exists()", "存在谓词", "返回判断不为 nil 的谓词"),
    SEQ_AND_P1_P2_P3("seq.and(p1, p2, p3, ...)", "逻辑与组合", "组合多个谓词函数，返回一个新的谓词函数，当今仅当 p1、p2、p3...等所有函数都返回 true 的时候，新函数返回 true"),
    SEQ_OR_P1_P2_P3("seq.or(p1, p2, p3, ...)", "逻辑或组合", "组合多个谓词函数，返回一个新的谓词函数，当 p1, p2, p3...其中一个返回 true 的时候，新函数立即返回 true，否则返回 false"),
    SEQ_MIN_COLL("seq.min(coll)", "最小值函数", "返回集合中的最小元素，要求集合元素可比较（实现 Comparable 接口），比较规则遵循 aviator 规则"),
    SEQ_MAX_COLL("seq.max(coll)", "最大值函数", "返回集合中的最大元素，要求集合元素可比较（实现 Comparable 接口），比较规则遵循 aviator 规则");

    // 枚举属性
    private final String functionSignature; // 函数签名（包含方法名和参数）
    private final String chineseName;      // 中文名称
    private final String description;      // 函数描述

    // 构造函数
    AviatorFunctionEnum(String functionSignature, String chineseName, String description) {
        this.functionSignature = functionSignature;
        this.chineseName = chineseName;
        this.description = description;
    }

    // Getter 方法
    public String getFunctionSignature() {
        return functionSignature;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 从完整签名中提取方法名
     */
    public String getMethodName() {
        int parenIndex = functionSignature.indexOf('(');
        if (parenIndex != -1) {
            return functionSignature.substring(0, parenIndex);
        }
        return functionSignature;
    }

    /**
     * 从完整签名中提取参数部分
     */
    public String getParameterPart() {
        int parenIndex = functionSignature.indexOf('(');
        if (parenIndex != -1 && functionSignature.endsWith(")")) {
            return functionSignature.substring(parenIndex + 1, functionSignature.length() - 1);
        }
        return "";
    }

    /**
     * 根据方法名查找枚举
     */
    public static AviatorFunctionEnum findByMethodName(String methodName) {
        for (AviatorFunctionEnum func : values()) {
            if (func.getMethodName().equals(methodName)) {
                return func;
            }
        }
        return null;
    }

    /**
     * 根据方法名查找所有匹配的枚举（处理重载）
     */
    public static List<AviatorFunctionEnum> findAllByMethodName(String methodName) {
        List<AviatorFunctionEnum> result = new ArrayList<>();
        for (AviatorFunctionEnum func : values()) {
            if (func.getMethodName().equals(methodName)) {
                result.add(func);
            }
        }
        return result;
    }

    /**
     * 根据中文名称查找枚举
     */
    public static AviatorFunctionEnum findByChineseName(String chineseName) {
        for (AviatorFunctionEnum func : values()) {
            if (func.chineseName.equals(chineseName)) {
                return func;
            }
        }
        return null;
    }

    /**
     * 根据完整签名查找枚举
     */
    public static AviatorFunctionEnum findByFullSignature(String signature) {
        for (AviatorFunctionEnum func : values()) {
            if (func.functionSignature.equals(signature)) {
                return func;
            }
        }
        return null;
    }

    /**
     * 判断是否为字符串相关函数
     */
    public boolean isStringFunction() {
        return functionSignature.startsWith("string.");
    }

    /**
     * 判断是否为数学相关函数
     */
    public boolean isMathFunction() {
        return functionSignature.startsWith("math.");
    }

    /**
     * 判断是否为序列相关函数
     */
    public boolean isSequenceFunction() {
        return functionSignature.startsWith("seq.") ||
                functionSignature.startsWith("repeat") ||
                functionSignature.startsWith("repeatedly") ||
                functionSignature.startsWith("map(") ||
                functionSignature.startsWith("filter(") ||
                functionSignature.startsWith("count(") ||
                functionSignature.startsWith("is_empty(") ||
                functionSignature.startsWith("distinct(") ||
                functionSignature.startsWith("concat(") ||
                functionSignature.startsWith("include(") ||
                functionSignature.startsWith("sort(") ||
                functionSignature.startsWith("reverse(") ||
                functionSignature.startsWith("reduce(") ||
                functionSignature.startsWith("take_while(") ||
                functionSignature.startsWith("drop_while(") ||
                functionSignature.startsWith("group_by(") ||
                functionSignature.startsWith("zipmap(");
    }

    /**
     * 根据完整签名查找枚举
     */
    public AviatorFunctionEnum convert(String signature) {
        for (AviatorFunctionEnum functionEnum : values()) {
            if (Objects.equals(functionEnum.functionSignature, signature)) {
                return functionEnum;
            }
        }

        return null;
    }

    /**
     * 获取所有函数的完整文档
     */
    public static String generateDocumentation() {
        StringBuilder sb = new StringBuilder();
        sb.append("AviatorScript 内置函数文档\n");
        sb.append("=".repeat(50)).append("\n\n");

        for (AviatorFunctionEnum func : values()) {
            sb.append(func.functionSignature).append("\n");
            sb.append("  ").append(func.description).append("\n\n");
        }

        return sb.toString();
    }

    /**
     * 按方法名分组生成文档
     */
    public static String generateGroupedDocumentation() {
        Map<String, List<AviatorFunctionEnum>> grouped = new HashMap<>();

        // 按方法名分组
        for (AviatorFunctionEnum func : values()) {
            String methodName = func.getMethodName();
            grouped.computeIfAbsent(methodName, k -> new ArrayList<>()).add(func);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("AviatorScript 内置函数（按方法名分组）\n");
        sb.append("=".repeat(50)).append("\n\n");

        for (Map.Entry<String, List<AviatorFunctionEnum>> entry : grouped.entrySet()) {
            sb.append(entry.getKey()).append("\n");
            sb.append("-".repeat(entry.getKey().length())).append("\n");

            for (AviatorFunctionEnum func : entry.getValue()) {
                sb.append("  ").append(func.functionSignature).append("\n");
                sb.append("     ").append(func.description).append("\n");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return "AviatorFunctionEnum{" +
                "functionSignature='" + functionSignature + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
