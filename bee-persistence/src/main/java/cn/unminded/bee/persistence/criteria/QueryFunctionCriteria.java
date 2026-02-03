package cn.unminded.bee.persistence.criteria;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class QueryFunctionCriteria {

    private Long id;

    /**
     * 函数英文名称
     */
    private String funcNameEn;

    private Integer funcType = 1;

    private Boolean asc = false;

    private Boolean desc = false;

}