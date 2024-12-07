package cn.unminded.bee.manage.dto.dict.response;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class QueryDictItemResponse {
    /**
     * 项编码
     */
    private String itemCode;

    /**
     * 项值
     */
    private String itemValue;
}
