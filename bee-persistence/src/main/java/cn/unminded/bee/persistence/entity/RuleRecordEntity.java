package cn.unminded.bee.persistence.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class RuleRecordEntity {

    private Long recordId;

    private Long modelId;

    private String ruleName;

    private String ruleContent;
}
