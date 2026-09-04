package cn.unminded.bee.core.rule.definition;

/**
 * @author lijunwei
 */
public class ElseAction {
    private String type; // 比如 assign
    private String field; // 比如 decisionResult
    private String operator; // 比如 =
    private String itemDesc; // 比如 赋值
    private String value; // 比如 拒绝

    public String getType() {
        return type;
    }

    public ElseAction setType(String type) {
        this.type = type;
        return this;
    }

    public String getField() {
        return field;
    }

    public ElseAction setField(String field) {
        this.field = field;
        return this;
    }

    public String getOperator() {
        return operator;
    }

    public ElseAction setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public ElseAction setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ElseAction setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "ElseAction{" +
                "type='" + type + '\'' +
                ", field='" + field + '\'' +
                ", operator='" + operator + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
