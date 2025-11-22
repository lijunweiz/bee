package cn.unminded.bee.persistence.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class FlowModelEntity {

    private Long flowModelId;

    /**
     * flowId{@link FlowEntity#getFlowId()}
     */
    private Long flowId;

    /**
     * modeId{@link ModelEntity#getId()}
     */
    private Long modelId;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}
