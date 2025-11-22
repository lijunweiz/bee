package cn.unminded.bee.persistence.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 变量管理实体
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class VariableEntity {

    /**
     * 变量id
     */
    private Long id;

    /**
     * 变量英文名称
     */
    private String variableNameEn;

    /**
     * 变量中文名称
     */
    private String variableNameZh;

    /**
     * 变量描述
     */
    private String variableDesc;

    /**
     * 数据源名称
     */
    private String dataSourceName;

    /**
     * 数据源类型
     */
    private String dataSourceType;

    /**
     * 变量当前的状态
     */
    private Integer variableStatus;

    /**
     * 变量版本
     */
    private Integer version;

    /**
     * 变量创建者
     */
    private String author;

    /**
     * 提出该变量的需求
     */
    private String requirementName;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}
