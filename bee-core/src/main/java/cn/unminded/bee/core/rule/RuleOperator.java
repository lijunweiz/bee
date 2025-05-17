package cn.unminded.bee.core.rule;

import java.util.Objects;

/**
 * 规则运算符
 * @author lijunwei
 */
public class RuleOperator {

    /**
     * 条件id
     */
    private String itemId;

    /**
     * 数据符号，如>、<=、!=
     */
    private String operator;

    /**
     * 中文描述，如大于、小于等于、不等于
     */
    private String itemDesc;

    public String getItemId() {
        return itemId;
    }

    public RuleOperator setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getOperator() {
        return operator;
    }

    public RuleOperator setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public RuleOperator setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleOperator that = (RuleOperator) o;
        return Objects.equals(itemId, that.itemId)
                && Objects.equals(operator, that.operator)
                && Objects.equals(itemDesc, that.itemDesc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, operator, itemDesc);
    }

    @Override
    public String toString() {
        return "RuleOperator{" +
                "itemId='" + itemId + '\'' +
                ", operator='" + operator + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                '}';
    }
}
