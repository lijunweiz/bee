package cn.unminded.bee.core.rule.definition;

/**
 * @author lijunwei
 */
public class RuleItem {

    private String nodeId;
    private Condition condition;
    private Action action;

    public String getNodeId() {
        return nodeId;
    }

    public RuleItem setNodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    public Condition getCondition() {
        return condition;
    }

    public RuleItem setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    public Action getAction() {
        return action;
    }

    public RuleItem setAction(Action action) {
        this.action = action;
        return this;
    }

    @Override
    public String toString() {
        return "RuleItem{" +
                "nodeId='" + nodeId + '\'' +
                ", condition=" + condition +
                ", action=" + action +
                '}';
    }
}
