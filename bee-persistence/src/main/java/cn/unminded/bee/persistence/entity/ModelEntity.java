package cn.unminded.bee.persistence.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class ModelEntity {
    /**
     * 模型id
     */
    private Long id;

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

    /**
     * 描述信息
     */
    private String modelDesc;

    /**
     * 启用状态，0-新建，1-启用，2-停用，3-废弃
     */
    private Integer modelStatus;

    /**
     * 操作员
     */
    private String operator;

    /**
     * 模型当前版本
     */
    private Integer currentModelVersion;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}
