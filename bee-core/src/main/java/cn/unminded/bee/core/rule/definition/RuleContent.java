package cn.unminded.bee.core.rule.definition;

import java.util.List;

/**
 * @author lijunwei
 */
public class RuleContent {
    private List<RuleItem> rules;
    private ElseAction elseAction;

    public List<RuleItem> getRules() {
        return rules;
    }

    public RuleContent setRules(List<RuleItem> rules) {
        this.rules = rules;
        return this;
    }

    public ElseAction getElseAction() {
        return elseAction;
    }

    public RuleContent setElseAction(ElseAction elseAction) {
        this.elseAction = elseAction;
        return this;
    }

    @Override
    public String toString() {
        return "RuleContent{" +
                "rules=" + rules +
                ", elseAction=" + elseAction +
                '}';
    }
}
