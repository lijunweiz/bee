package cn.unminded.bee.persistence.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class VariableMetadataEntity {

    private Long metaId;

    /**
     * 变量类型
     */
    private String variableType;

    /**
     * 变量来源
     */
    private String variableSource;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
