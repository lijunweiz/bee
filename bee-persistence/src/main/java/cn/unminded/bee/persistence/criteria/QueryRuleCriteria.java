package cn.unminded.bee.persistence.criteria;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class QueryRuleCriteria {
    /**
     * rule id
     */
    private Long id;

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
     * 规则组
     */
    private Integer ruleGroup;

    /**
     * 规则名称
     */
    private String ruleName;

    private Integer status;


}
