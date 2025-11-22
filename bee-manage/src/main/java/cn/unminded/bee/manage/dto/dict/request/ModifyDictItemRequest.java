package cn.unminded.bee.manage.dto.dict.request;

import cn.unminded.bee.common.annotation.DictStatus;
import lombok.Data;

/**
 * @author lijunwei
 */
@Data
public class ModifyDictItemRequest {

    private Long id;

    /**
     * 类型编码
     */
    private String dictCode;

    /**
     * 项编码
     */
    private String itemCode;

    /**
     * 项名称
     */
    private String itemName;

    /**
     * 项值
     */
    private String itemValue;

    /**
     * 状态，1表示启用，0表示禁用
     * {@link DictStatus}
     */
    @DictStatus(required = false)
    private Integer status;

    /**
     * 顺序
     */
    private Integer sort;

    /**
     * 描述信息
     */
    private String description;
}
