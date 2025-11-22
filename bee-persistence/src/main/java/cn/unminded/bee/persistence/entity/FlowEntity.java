package cn.unminded.bee.persistence.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 流程表
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class FlowEntity {

    /**
     * 流程id
     */
    private Long flowId;

    /**
     * 流程类型
     */
    private String flowType;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 流程当前版本
     */
    private Integer currentFlowVersion;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
