package cn.unminded.bee.turn.dto.aviator.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class AviatorFuncResponse {
    private String funcNameEn;
    /** 中文名 */
    private String funcNameZh;
    /** 1-系统内置,2-自定义,3-第三方扩展 */
    private Integer funcType;
    /** 函数功能描述 */
    private String funcDesc;
}
