package cn.unminded.bee.persistence.entity;

import cn.unminded.bee.common.annotation.DictStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 字典表
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class DictEntity {

    private Long dictId;

    /**
     * 类型编码
     */
    @NotBlank(message = "typeCode不能为空")
    private String typeCode;

    /**
     * 项编码
     */
    @NotBlank(message = "itemCode不能为空")
    private String itemCode;

    /**
     * 项名称
     */
    @NotBlank(message = "itemName不能为空")
    private String itemName;

    /**
     * 项值
     */
    @NotBlank(message = "itemValue不能为空")
    private String itemValue;

    /**
     * 描述信息
     */
    @NotBlank(message = "itemDesc不能为空")
    private String itemDesc;

    /**
     * 状态，1表示启用，0表示禁用
     */
    @DictStatus
    private Integer itemStatus;

    /**
     * 顺序
     */
    @NotNull(message = "sort不能为null")
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}