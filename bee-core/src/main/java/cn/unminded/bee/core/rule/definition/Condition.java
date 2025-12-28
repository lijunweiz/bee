package cn.unminded.bee.core.rule.definition;

import java.util.List;

/**
 * @author lijunwei
 */
public class Condition {
    private String operator; // e.g. AND
    private List<ConditionChild> children;

    public String getOperator() {
        return operator;
    }

    public Condition setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public List<ConditionChild> getChildren() {
        return children;
    }

    public Condition setChildren(List<ConditionChild> children) {
        this.children = children;
        return this;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "operator='" + operator + '\'' +
                ", children=" + children +
                '}';
    }
}
