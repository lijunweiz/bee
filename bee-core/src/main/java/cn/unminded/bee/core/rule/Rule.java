package cn.unminded.bee.core.rule;

import cn.unminded.bee.core.RuleContext;
import cn.unminded.bee.core.RuleItem;
import cn.unminded.bee.core.RuleOutput;

/**
 * 所有规则的核心接口, 每一个规则必须实现改接口, 执行完毕输出结果
 * @author lijunwei
 */
public interface Rule {

    RuleItem apply(RuleContext ruleContext);

}
