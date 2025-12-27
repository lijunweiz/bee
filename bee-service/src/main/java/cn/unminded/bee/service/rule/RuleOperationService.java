package cn.unminded.bee.service.rule;

import cn.unminded.bee.persistence.criteria.QueryRuleCriteria;
import cn.unminded.bee.persistence.entity.RuleRecordEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface RuleOperationService {

    List<RuleRecordEntity> query(QueryRuleCriteria criteria);

    Integer save(RuleRecordEntity ruleRecordEntity);

    Integer update(RuleRecordEntity dictItemEntity);

}
