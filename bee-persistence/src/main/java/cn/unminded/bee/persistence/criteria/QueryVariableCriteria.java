package cn.unminded.bee.persistence.criteria;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class QueryVariableCriteria {

    private String variableNameEn;

    private String variableSource;

    private String variableType;

    private boolean ascending = false;

    private Integer startIndex;

    private Integer endIndex;

    private String startTime;

    private String endTime;

}
