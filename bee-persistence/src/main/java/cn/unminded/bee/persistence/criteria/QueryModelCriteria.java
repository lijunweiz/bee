package cn.unminded.bee.persistence.criteria;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class QueryModelCriteria {
    /**
     * 模型id
     */
    private Long modelId;

    /**
     * 模型类型
     */
    private String modelType;

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 叶子节点，0-否，1-是
     */
    private Integer isLeaf;

}
