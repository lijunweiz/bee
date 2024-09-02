package cn.unminded.bee.manage.dto.variable.request;

import cn.unminded.bee.common.constant.VariableStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lijunwei
 */
@Data
public class ModifyVariableStatusRequest {

    /**
     * 变量英文名称
     */
    @NotBlank(message = "变量英文名称不能为空")
    private String variableNameEn;

    /**
     * 变量作者
     */
    @NotBlank(message = "变量作者不能为空")
    private String author;

    /**
     * {@link VariableStatusEnum#getStatus()}
     */
    private Integer variableStatus;

}
