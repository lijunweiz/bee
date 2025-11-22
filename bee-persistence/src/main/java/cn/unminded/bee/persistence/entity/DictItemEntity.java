package cn.unminded.bee.persistence.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 字典项表
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class DictItemEntity {

    private Long id;

    /**
     * 字典编码
     */
    @NotBlank(message = "dictCode不能为空")
    private String dictCode;

    /**
     * 字典项编码
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
     * 状态，1表示启用，0表示禁用
     */
    private Integer status;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}