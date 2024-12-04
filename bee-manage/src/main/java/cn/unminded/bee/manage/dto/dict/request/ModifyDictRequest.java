package cn.unminded.bee.manage.dto.dict.request;

import cn.unminded.bee.common.annotation.DictStatus;
import lombok.Data;

/**
 * @author lijunwei
 */
@Data
public class ModifyDictRequest {

    private Long dictId;

    /**
     * 类型编码
     */
    private String typeCode;

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
     * 描述信息
     */
    private String itemDesc;
    /**
     * 状态，1表示启用，0表示禁用
     * {@link DictStatus}
     */
    @DictStatus(required = false)
    private Integer itemStatus;
    /**
     * 顺序
     */
    private Integer sort;
}
