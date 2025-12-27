package cn.unminded.bee.turn.dto.dict.request;

import cn.unminded.bee.common.annotation.DictStatus;
import lombok.Data;

/**
 * @author lijunwei
 */
@Data
public class ModifyDictRequest {

    private Long id;

    /**
     * 类型编码
     */
    private String dictCode;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 状态，1表示启用，0表示禁用
     * {@link DictStatus}
     */
    @DictStatus(required = false)
    private Integer status;

    /**
     * 描述信息
     */
    private String description;

}
