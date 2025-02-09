package cn.unminded.bee.persistence.criteria;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class DeleteModelCriteria {
    /**
     * 模型id
     */
    private Long modelId;

    /**
     * 叶子节点，0-否，1-是
     */
    private Integer isLeaf;
}
