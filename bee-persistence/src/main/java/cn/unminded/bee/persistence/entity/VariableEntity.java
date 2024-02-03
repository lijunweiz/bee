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
    private Long variableId;

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
     * 变量类型
     */
    private String variableType;

    /**
     * 变量来源
     */
    private String variableSource;

    /**
     * 获取变量时的入参
     */
    private String variableParam;

    /**
     * 变量当前的状态
     */
    private Integer variableStatus;

    /**
     * 变量版本
     */
    private Integer variableVersion;

    /**
     * 获取变量时的地址
     */
    private String variableAddress;

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
    private LocalDateTime updateTime;

}
