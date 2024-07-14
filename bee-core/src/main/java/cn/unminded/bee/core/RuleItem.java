package cn.unminded.bee.core;

/**
 * 规则项
 * @author lijunwei
 */
public class RuleItem {

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 当前值
     */
    private Object ruleValue;

    /**
     * 通过还是拒绝
     */
    private Boolean pass;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    public String getRuleName() {
        return ruleName;
    }

    public RuleItem setRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public Object getRuleValue() {
        return ruleValue;
    }

    public RuleItem setRuleValue(Object ruleValue) {
        this.ruleValue = ruleValue;
        return this;
    }

    public Boolean getPass() {
        return pass;
    }

    public RuleItem setPass(Boolean pass) {
        this.pass = pass;
        return this;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public RuleItem setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
        return this;
    }

    @Override
    public String toString() {
        return "RuleOutput{" +
                "ruleName='" + ruleName + '\'' +
                ", ruleValue=" + ruleValue +
                ", pass=" + pass +
                ", rejectReason='" + rejectReason + '\'' +
                '}';
    }
}
