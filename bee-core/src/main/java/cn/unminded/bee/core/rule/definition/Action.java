package cn.unminded.bee.core.rule.definition;

/**
 * @author lijunwei
 */
public class Action {
    private String type; // e.g. assign
    private String field; // e.g. decisionResult
    private String operator; // e.g. =
    private String itemDesc; // e.g. 赋值
    private String value; // e.g. 通过

    public String getType() {
        return type;
    }

    public Action setType(String type) {
        this.type = type;
        return this;
    }

    public String getField() {
        return field;
    }

    public Action setField(String field) {
        this.field = field;
        return this;
    }

    public String getOperator() {
        return operator;
    }

    public Action setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public Action setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Action setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "Action{" +
                "type='" + type + '\'' +
                ", field='" + field + '\'' +
                ", operator='" + operator + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

