package cn.unminded.bee.manage.dto.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author lijunwei
 */
@Data
public class ModelTreeItemRequest {

    /**
     * 模型类型
     */
    @NotBlank(message = "模型类型不能为空")
    private String modelType;

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 叶子节点，0-否，1-是
     */
    @NotNull(message = "必须说明是否是叶子节点")
    private Integer isLeaf;

    /**
     * 描述信息
     */
    @NotBlank(message = "描述信息不能为空")
    private String modelDesc;

    /**
     * 操作员
     */
    @NotBlank(message = "操作员不能为空")
    private String operator;

}
