package cn.unminded.bee.persistence.criteria;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class QueryDataSourceCriteria {

    private Long dataSourceId;

    private String dataSourceName;

    private String dataSourceType;

    private Integer dataSourceStatus;

    private boolean ascending = false;

    private Integer start;

    private Integer limit;

    private String startTime;

    private String endTime;

}
