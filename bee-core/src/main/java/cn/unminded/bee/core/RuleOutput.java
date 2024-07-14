package cn.unminded.bee.core;

import java.util.List;

/**
 * @author lijunwei
 */
public class RuleOutput {

    /**
     * 最终结果
     */
    private Boolean finalResult;

    /**
     * 最终拒绝原因
     */
    private String finalRejectReason;

    /**
     * 该数据经历哪些决策项
     */
    private List<RuleItem> ruleItemList;

    public Boolean getFinalResult() {
        return finalResult;
    }

    public RuleOutput setFinalResult(Boolean finalResult) {
        this.finalResult = finalResult;
        return this;
    }

    public String getFinalRejectReason() {
        return finalRejectReason;
    }

    public RuleOutput setFinalRejectReason(String finalRejectReason) {
        this.finalRejectReason = finalRejectReason;
        return this;
    }

    public List<RuleItem> getRuleItemList() {
        return ruleItemList;
    }

    public RuleOutput setRuleItemList(List<RuleItem> ruleItemList) {
        this.ruleItemList = ruleItemList;
        return this;
    }

    @Override
    public String toString() {
        return "RuleOutput{" +
                "finalResult=" + finalResult +
                ", finalRejectReason='" + finalRejectReason + '\'' +
                ", ruleItemList=" + ruleItemList +
                '}';
    }
}
