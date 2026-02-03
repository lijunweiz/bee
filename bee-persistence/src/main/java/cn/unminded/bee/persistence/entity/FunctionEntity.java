package cn.unminded.bee.persistence.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 *
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class FunctionEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 函数英文名称
     */
    @NotBlank(message = "funcNameEn不能为空")
    private String funcNameEn;

    /**
     * 函数中文
     */
    @NotBlank(message = "funcNameZh不能为空")
    private String funcNameZh;

    /**
     * 1-系统内置,2-自定义,3-第三方扩展
     */
    private Integer funcType;

    /**
     * 函数具体实现
     */
    private String func;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 状态0表示停用,1表示启用
     */
    private Integer status;

    /**
     *  版本号
     */
    private Integer version;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}
