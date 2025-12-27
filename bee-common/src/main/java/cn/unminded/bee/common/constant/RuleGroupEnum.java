package cn.unminded.bee.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

/**
 * @author lijunwei
 */
@ToString
@Getter
@AllArgsConstructor
public enum RuleGroupEnum {

    DECISION_GROUP(1, "decision", "决策组"),

    REJECT_GROUP(2, "reject", "拒绝组"),

    MONITOR_GROUP(3, "monitor", "监控组"),

    ;

    private final Integer code;

    private final String key;

    private final String name;

    public RuleGroupEnum convert(Integer code) {
        for (RuleGroupEnum ruleGroupEnum : RuleGroupEnum.values()) {
            if (ruleGroupEnum.code.equals(code)) {
                return ruleGroupEnum;
            }
        }

        return null;
    }

    public RuleGroupEnum convertByKey(String key) {
        for (RuleGroupEnum ruleGroupEnum : RuleGroupEnum.values()) {
            if (ruleGroupEnum.key.equals(key)) {
                return ruleGroupEnum;
            }
        }

        return null;
    }

    public static List<RuleGroupEnum> list() {
        return Arrays.asList(values());
    }

}
