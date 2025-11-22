package cn.unminded.bee.persistence.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijunwei
 */
@Accessors(chain = true)
@Data
public class RuleRecordEntity {

    private Long id;

    private Long modelId;

    private String ruleName;

    private String ruleContent;
}
