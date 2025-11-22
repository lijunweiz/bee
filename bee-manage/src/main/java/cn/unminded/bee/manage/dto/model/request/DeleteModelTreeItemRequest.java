package cn.unminded.bee.manage.dto.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lijunwei
 */
@Data
public class DeleteModelTreeItemRequest {

    /**
     * 模型id 数据更新时必须
     */
    @NotNull(message = "id不能为null")
    private Long id;

    /**
     * 叶子节点，0-否，1-是
     */
    @NotNull(message = "必须说明是否是叶子节点")
    private Integer isLeaf;

}
