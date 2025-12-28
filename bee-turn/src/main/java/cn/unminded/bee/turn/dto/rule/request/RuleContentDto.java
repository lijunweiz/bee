package cn.unminded.bee.turn.dto.rule.request;

import cn.unminded.bee.core.rule.definition.RuleContent;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lijunwei
 */
@Data
public class RuleContentDto {

    @NotNull(message = "模型id不能为空")
    private Long modelId;

    @NotNull(message = "ruleId不能为空")
    private Long ruleId;

    @NotNull(message = "规则内容不能为null")
    private RuleContent ruleContent;

    private String description;


}
