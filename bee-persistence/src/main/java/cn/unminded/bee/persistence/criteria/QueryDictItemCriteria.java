package cn.unminded.bee.persistence.criteria;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class QueryDictItemCriteria {

    private Long id;

    /**
     * 字典编码
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
     * 状态
     */
    private Integer status;

    /**
     * 开始位置
     */
    private Integer start;

    /**
     * 分页大小
     */
    private Integer limit;

}