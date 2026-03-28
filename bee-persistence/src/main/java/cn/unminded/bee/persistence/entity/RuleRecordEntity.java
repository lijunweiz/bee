package cn.unminded.bee.persistence.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class RuleRecordEntity {

    private Long id;

    /**
     * 关联modelId{@link ModelEntity#getId()}
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
     * 规则类型
     */
    private Integer ruleGroup;

    /**
     * 规则名
     */
    private String ruleName;

    /**
     * 规则内容
     */
    private String ruleContent;

    /**
     * 变量id列表
     */
    private String variableIdList;

    private Integer status;
    /**
     * 规则描述
     */
    private String description;

    private String version;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
}
