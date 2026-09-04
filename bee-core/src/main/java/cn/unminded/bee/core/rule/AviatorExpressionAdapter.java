package cn.unminded.bee.core.rule;

import cn.unminded.bee.core.rule.definition.RuleContent;
import cn.unminded.bee.core.util.BeeCoreExceptionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Aviator 表达式适配器。
 *
 * <p>当前只负责把前端规则编辑器提交的 RuleContent json 适配为 Aviator 可执行表达式。
 * 这个类不参与原有规则保存、读取、执行流程，只提供独立的转换能力。</p>
 *
     * <p>整体规则使用嵌套三元表达式表达顺序命中逻辑：
     * 第一条规则命中则返回第一条 action 对应的赋值结果，否则继续判断下一条规则，
     * 全部不命中则返回 elseAction 对应的赋值结果。</p>
 */
public final class AviatorExpressionAdapter {

    private static final String RULES = "rules";
    private static final String CONDITION = "condition";
    private static final String ACTION = "action";
    private static final String ELSE_ACTION = "elseAction";
    private static final String CHILDREN = "children";
    private static final String OPERATOR = "operator";
    private static final String FIELD = "field";
    private static final String OP = "op";
    private static final String VALUE = "value";

    private static final String NULL = "null";

    /**
     * Aviator 变量名或点路径校验。
     *
     * <p>允许 firstLoanTime、user.age 这类字段；不允许 a + b、user-name 这类内容。
     * 转换器会把 field 直接拼进表达式，所以这里要先限制字段格式，避免拼出非法表达式。</p>
     */
    private static final Pattern IDENTIFIER_PATH = Pattern.compile("[A-Za-z_][A-Za-z0-9_]*(\\.[A-Za-z_][A-Za-z0-9_]*)*");

    /**
     * 整数字符串识别。
     *
     * <p>前端提交的 value 可能是字符串 "10"，但业务含义是数字 10。
     * 命中该模式时直接输出数字字面量，不再输出带引号的字符串。</p>
     */
    private static final Pattern INTEGER = Pattern.compile("[-+]?\\d+");

    /**
     * 小数字符串识别。
     *
     * <p>比如 "10.5" 会输出为 10.5，便于 Aviator 按数字参与大小比较。</p>
     */
    private static final Pattern DECIMAL = Pattern.compile("[-+]?(\\d+\\.\\d*|\\d*\\.\\d+)");

    private AviatorExpressionAdapter() {
    }

    /**
     * 将完整 RuleContent json 字符串转换为 Aviator 表达式。
     *
     * @param ruleContentJson RuleContent 对应的 json 字符串
     * @return Aviator 表达式
     */
    public static String toExpression(String ruleContentJson) {
        check(isBlank(ruleContentJson), "ruleContentJson 不能为空");
        return toExpression(JSON.parseObject(ruleContentJson));
    }

    /**
     * 将 RuleContent 对象转换为 Aviator 表达式。
     *
     * <p>这里先转成 JSONObject 再处理，是为了统一递归逻辑。现有 RuleContent/ConditionChild
     * Java 模型无法表达 sample.json 中 children 内继续嵌套条件组的结构，直接处理 json 树更稳妥。</p>
     *
     * @param ruleContent 规则内容对象
     * @return Aviator 表达式
     */
    public static String toExpression(RuleContent ruleContent) {
        check(Objects.isNull(ruleContent), "ruleContent 不能为 null");
        return toExpression((JSONObject) JSON.toJSON(ruleContent));
    }

    /**
     * 将完整 RuleContent json 对象转换为 Aviator 表达式。
     *
     * <p>转换结果示例：</p>
     * <pre>
     * ((firstLoanTime &gt;= 10) ? (seq.map("decisionResult", "通过")) : (seq.map("decisionResult", "拒绝")))
     * </pre>
     *
     * @param ruleContent RuleContent 对应的 json 对象
     * @return Aviator 表达式
     */
    public static String toExpression(JSONObject ruleContent) {
        check(Objects.isNull(ruleContent), "ruleContent 不能为 null");

        JSONArray rules = ruleContent.getJSONArray(RULES);
        String elseExpression = toActionExpression(ruleContent.getJSONObject(ELSE_ACTION));
        if (isEmpty(rules)) {
            return elseExpression;
        }

        String expression = elseExpression;
        // 从后向前组装三元表达式，用规则顺序表达 if/else if/else 的命中逻辑。
        for (int i = rules.size() - 1; i >= 0; i--) {
            JSONObject rule = rules.getJSONObject(i);
            String conditionExpression = toConditionExpression(rule.getJSONObject(CONDITION));
            String actionExpression = toActionExpression(rule.getJSONObject(ACTION));
            expression = "((" + conditionExpression + ") ? (" + actionExpression + ") : (" + expression + "))";
        }
        return expression;
    }

    /**
     * 将单个 condition json 字符串转换为 Aviator 条件表达式。
     *
     * @param conditionJson condition 对应的 json 字符串
     * @return Aviator 条件表达式
     */
    public static String toConditionExpression(String conditionJson) {
        check(isBlank(conditionJson), "conditionJson 不能为空");
        return toConditionExpression(JSON.parseObject(conditionJson));
    }

    /**
     * 将单个 condition json 对象转换为 Aviator 条件表达式。
     *
     * @param condition condition 对应的 json 对象
     * @return Aviator 条件表达式
     */
    public static String toConditionExpression(JSONObject condition) {
        check(Objects.isNull(condition), "condition 不能为 null");
        return toNodeExpression(condition);
    }

    /**
     * 递归转换条件节点。
     *
     * <p>条件节点分两类：</p>
     * <ul>
     *     <li>条件组：包含 operator 和 children，例如 AND/OR/NOT。</li>
     *     <li>条件项：包含 field、op、value，例如 firstLoanTime &gt;= 10。</li>
     * </ul>
     */
    private static String toNodeExpression(JSONObject node) {
        check(Objects.isNull(node), "条件节点不能为 null");
        // 包含 children 的节点按条件组递归处理，否则按 field/op/value 条件项处理。
        if (node.containsKey(CHILDREN)) {
            return toGroupExpression(node);
        }
        return toLeafExpression(node);
    }

    /**
     * 转换条件组。
     *
     * <p>条件组会把 children 内每个子节点递归转换后，再用标准 Aviator 逻辑运算符连接。
     * 每个子表达式都会加括号，避免嵌套 AND/OR 时运算优先级导致结果偏差。</p>
     */
    private static String toGroupExpression(JSONObject group) {
        JSONArray children = group.getJSONArray(CHILDREN);
        check(isEmpty(children), "条件组 children 不能为空");

        String operator = normalizeLogicalOperator(group.getString(OPERATOR));
        List<String> childExpressions = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            childExpressions.add("(" + toNodeExpression(children.getJSONObject(i)) + ")");
        }

        if ("!".equals(operator)) {
            check(childExpressions.size() != 1, "NOT 条件组只能包含一个子节点");
            return "!" + childExpressions.get(0);
        }
        return String.join(" " + operator + " ", childExpressions);
    }

    /**
     * 转换叶子条件项。
     *
     * <p>叶子节点要求存在 field、op、value，并转换成 Aviator 的二元比较表达式。</p>
     */
    private static String toLeafExpression(JSONObject leaf) {
        String field = validateField(leaf.getString(FIELD));
        String originalOperator = leaf.getString(OP);
        if (isContainsOperator(originalOperator)) {
            return toContainsExpression(field, originalOperator, leaf.get(VALUE));
        }
        String operator = normalizeCompareOperator(originalOperator);
        String value = toLiteral(leaf.get(VALUE));
        return field + " " + operator + " " + value;
    }

    /**
     * 转换动作节点。
     *
     * <p>当前规则结构中的 action/elseAction 表示赋值动作。action.field 是输出字段名，
     * 可能不是合法 Aviator 变量名，所以不能生成 {@code field = value} 这种左值赋值表达式。</p>
     *
     * <p>这里统一生成 {@code seq.map("field", value)}，表达式执行后返回一个包含赋值结果的 Map。
     * 由于外层使用三元表达式，只有条件满足的分支才会执行对应 action 的 value 转换结果。</p>
     */
    private static String toActionExpression(JSONObject action) {
        if (Objects.isNull(action)) {
            return NULL;
        }
        String field = toActionFieldLiteral(action.getString(FIELD));
        normalizeActionOperator(action.getString(OPERATOR));
        String value = toLiteral(action.get(VALUE));
        return "seq.map(" + field + ", " + value + ")";
    }

    /**
     * 归一化逻辑运算符。
     *
     * <p>前端或配置可以传 AND/OR/NOT，也可以直接传 Aviator 支持的 &&/||/!，
     * 输出统一使用 Aviator 运算符。</p>
     */
    private static String normalizeLogicalOperator(String operator) {
        check(isBlank(operator), "逻辑运算符不能为空");
        String normalized = operator.trim();
        if ("AND".equalsIgnoreCase(normalized) || "&&".equals(normalized)) {
            return "&&";
        }
        if ("OR".equalsIgnoreCase(normalized) || "||".equals(normalized)) {
            return "||";
        }
        if ("NOT".equalsIgnoreCase(normalized) || "!".equals(normalized)) {
            return "!";
        }
        throw BeeCoreExceptionUtil.build("不支持的逻辑运算符: " + operator);
    }

    /**
     * 归一化比较运算符。
     *
     * <p>规则配置里的 = 会转换为 Aviator 比较运算符 ==；字符串相等也使用 ==，
     * 字符串值会由 toLiteral 方法自动转成带引号的 Aviator 字符串字面量。</p>
     *
     * <p>in/notIn 不属于普通二元比较运算符，会在 toLeafExpression 中单独映射为
     * include(seq.list(...), field) 或 !include(seq.list(...), field)。</p>
     */
    private static String normalizeCompareOperator(String operator) {
        check(isBlank(operator), "比较运算符不能为空");
        String normalized = operator.trim();
        switch (normalized) {
            case "=":
            case "==":
            case "!=":
            case ">":
            case ">=":
            case "<":
            case "<=":
                return "=".equals(normalized) ? "==" : normalized;
            case "eq":
                return "==";
            case "ne":
                return "!=";
            case "gt":
                return ">";
            case "ge":
                return ">=";
            case "lt":
                return "<";
            case "le":
                return "<=";
            default:
                throw BeeCoreExceptionUtil.build("不支持的比较运算符: " + operator);
        }
    }

    /**
     * 判断是否为集合包含类运算符。
     *
     * <p>支持 in、notIn，最终统一映射到
     * Aviator 的 include(seq, element) 函数。</p>
     */
    private static boolean isContainsOperator(String operator) {
        if (isBlank(operator)) {
            return false;
        }
        String normalized = normalizeContainsOperator(operator);
        return "in".equals(normalized) || "notIn".equals(normalized);
    }

    /**
     * 转换 in/not in 条件。
     *
     * <p>AviatorFunctionEnum 中已有 include(seq, element) 函数，语义是判断 element
     * 是否在集合 seq 中，所以规则里的 field in valueList 映射为 include(seq.list(...), field)。</p>
     */
    private static String toContainsExpression(String field, String operator, Object value) {
        String includeExpression = "include(" + toSequenceExpression(value) + ", " + field + ")";
        if ("notIn".equals(normalizeContainsOperator(operator))) {
            return "!(" + includeExpression + ")";
        }
        return includeExpression;
    }

    /**
     * 归一化集合包含运算符。
     */
    private static String normalizeContainsOperator(String operator) {
        String normalized = operator.trim();
        if ("in".equals(normalized)) {
            return "in";
        }
        if ("notIn".equals(normalized)) {
            return "notIn";
        }
        return normalized;
    }

    /**
     * 将规则 value 转成 Aviator 序列表达式。
     *
     * <p>推荐前端传 json 数组，比如 ["A","B"]，会生成 seq.list("A", "B")。
     * 为了兼容简单录入，也支持字符串 "A,B" 转成 seq.list("A", "B")；如果元素本身包含逗号，
     * 应该使用 json 数组，避免被错误拆分。</p>
     */
    private static String toSequenceExpression(Object value) {
        check(Objects.isNull(value), "in 运算符取值不能为 null");
        if (value instanceof JSONArray) {
            return toSequenceExpression((JSONArray) value);
        }
        if (value instanceof Collection) {
            return toSequenceExpression((Collection<?>) value);
        }
        if (value instanceof String) {
            return toSequenceExpression((String) value);
        }
        return "seq.list(" + toLiteral(value) + ")";
    }

    /**
     * 将 JSONArray 转成 seq.list(...)。
     */
    private static String toSequenceExpression(JSONArray values) {
        List<String> literals = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            literals.add(toLiteral(values.get(i)));
        }
        return "seq.list(" + String.join(", ", literals) + ")";
    }

    /**
     * 将 Java 集合转成 seq.list(...)。
     */
    private static String toSequenceExpression(Collection<?> values) {
        List<String> literals = new ArrayList<>();
        for (Object value : values) {
            literals.add(toLiteral(value));
        }
        return "seq.list(" + String.join(", ", literals) + ")";
    }

    /**
     * 将字符串形式的集合值转成 seq.list(...)。
     *
     * <p>字符串如果是 json 数组文本，优先按 json 数组解析；否则按逗号拆分。
     * 普通单值字符串会变成只有一个元素的 seq.list(...)。</p>
     */
    private static String toSequenceExpression(String value) {
        check(isBlank(value), "in 运算符取值不能为空");
        String normalized = value.trim();
        if (normalized.startsWith("[") && normalized.endsWith("]")) {
            try {
                return toSequenceExpression(JSON.parseArray(normalized));
            } catch (Exception e) {
                throw BeeCoreExceptionUtil.build("in 运算符 JSON 数组格式错误: " + value, e);
            }
        }

        List<String> literals = new ArrayList<>();
        String[] parts = normalized.split(",");
        for (String part : parts) {
            literals.add(toStringLiteral(part.trim()));
        }
        return "seq.list(" + String.join(", ", literals) + ")";
    }

    /**
     * 归一化动作运算符。
     *
     * <p>当前只支持赋值动作，后续如果需要支持 append、remove、函数调用等动作，
     * 可以在这里扩展动作运算符和对应转换规则。</p>
     */
    private static String normalizeActionOperator(String operator) {
        if (isBlank(operator) || "=".equals(operator.trim())) {
            return "=";
        }
        throw BeeCoreExceptionUtil.build("不支持的动作运算符: " + operator);
    }

    /**
     * 将动作字段名转换为字符串字面量。
     *
     * <p>动作字段名表示输出结果里的 key，不参与 Aviator 变量读取，所以允许任意非空字符串。
     * 使用 JSON.toJSONString 做转义，避免字段名中包含引号、空格、中文或特殊字符时破坏表达式。</p>
     */
    private static String toActionFieldLiteral(String field) {
        check(isBlank(field), "动作字段名不能为空");
        return JSON.toJSONString(field);
    }

    /**
     * 校验字段名是否适合作为 Aviator 变量路径。
     *
     * <p>这里限制为 Java 风格标识符和点路径，例如 user.age、firstLoanTime。
     * 不允许把任意字符串拼进表达式，避免生成语法错误或注入额外表达式片段。</p>
     */
    private static String validateField(String field) {
        check(isBlank(field), "字段名不能为空");
        String normalized = field.trim();
        check(!IDENTIFIER_PATH.matcher(normalized).matches(), "不支持的字段名: " + field);
        return normalized;
    }

    /**
     * 将 json 值转换为 Aviator 字面量。
     *
     * <p>数字和布尔值直接输出；Java null 输出为 null；字符串会继续判断是否是数字、
     * 布尔或 null 文本，否则按 JSON 字符串转义后输出，保证中文和引号等字符不会破坏表达式。</p>
     */
    private static String toLiteral(Object value) {
        if (Objects.isNull(value)) {
            return NULL;
        }
        if (value instanceof Number || value instanceof Boolean) {
            return String.valueOf(value);
        }
        if (value instanceof String) {
            return toStringLiteral((String) value);
        }
        return JSON.toJSONString(value);
    }

    /**
     * 将字符串值转换为 Aviator 字面量。
     *
     * <p>前端示例里 value 常以字符串形式提交，例如 "10"。这里会把可识别的数字字符串
     * 转成数字字面量，普通文本则转成带引号的字符串字面量。</p>
     */
    private static String toStringLiteral(String value) {
        String normalized = value.trim();
        if ("true".equalsIgnoreCase(normalized) || "false".equalsIgnoreCase(normalized)) {
            return normalized.toLowerCase();
        }
        if ("null".equalsIgnoreCase(normalized)) {
            return NULL;
        }
        if (INTEGER.matcher(normalized).matches() || DECIMAL.matcher(normalized).matches()) {
            return normalized;
        }
        return JSON.toJSONString(value);
    }

    /**
     * 判断 JSONArray 是否为空。
     */
    private static boolean isEmpty(JSONArray array) {
        return Objects.isNull(array) || array.isEmpty();
    }

    /**
     * 判断字符串是否为空白。
     */
    private static boolean isBlank(String value) {
        return Objects.isNull(value) || value.trim().isEmpty();
    }

    /**
     * 统一使用 BeeCoreExceptionUtil 抛出业务异常。
     */
    private static void check(boolean condition, String message) {
        BeeCoreExceptionUtil.trueToThrow(condition, message);
    }
}
