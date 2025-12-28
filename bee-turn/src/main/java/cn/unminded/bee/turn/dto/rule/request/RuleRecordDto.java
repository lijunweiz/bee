package cn.unminded.bee.turn.dto.rule.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author lijunwei
 */
@Data
public class RuleRecordDto {

    @NotNull(message = "模型id不能为空")
    private Long modelId;

    @NotNull(message = "规则类型不能为空")
    private Integer ruleGroup;

    @NotBlank(message = "规则名称不能为空")
    private String ruleName;

    private String description;


}
