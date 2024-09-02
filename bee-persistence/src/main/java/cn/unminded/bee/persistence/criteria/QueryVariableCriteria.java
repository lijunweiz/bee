package cn.unminded.bee.persistence.criteria;

import cn.unminded.bee.common.constant.BeeConstant;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class QueryVariableCriteria {

    private String variableNameEn;

    private String dataSourceName;

    private String dataSourceType;

    private boolean ascending = false;

    private Integer start;

    private Integer limit;

    private String startTime = BeeConstant.BEE_START_TIME;

    private String endTime;

}
