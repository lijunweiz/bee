package cn.unminded.bee.persistence.criteria;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class QueryDictCriteria {

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
     * 状态
     */
    private Integer itemStatus;

    /**
     * 开始位置
     */
    private Integer start;

    /**
     * 分页大小
     */
    private Integer limit;

}