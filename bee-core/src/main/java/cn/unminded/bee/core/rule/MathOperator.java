package cn.unminded.bee.core.rule;

import java.util.Objects;

/**
 * 数学运算符
 * @author lijunwei
 */
public class MathOperator {

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

    public MathOperator setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getOperator() {
        return operator;
    }

    public MathOperator setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public MathOperator setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathOperator that = (MathOperator) o;
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
        return "MathOperator{" +
                "itemId='" + itemId + '\'' +
                ", operator='" + operator + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                '}';
    }
}
