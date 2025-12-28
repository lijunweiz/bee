package cn.unminded.bee.core.rule.definition;

/**
 * @author lijunwei
 */
public class ConditionChild {
    private String field; // e.g. firstLoanTime
    private String op; // e.g. >=
    private String itemDesc; // e.g. 等于
    private String value; // e.g. 30

    public String getField() {
        return field;
    }

    public ConditionChild setField(String field) {
        this.field = field;
        return this;
    }

    public String getOp() {
        return op;
    }

    public ConditionChild setOp(String op) {
        this.op = op;
        return this;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public ConditionChild setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ConditionChild setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "ConditionChild{" +
                "field='" + field + '\'' +
                ", op='" + op + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

