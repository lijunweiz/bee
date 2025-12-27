package cn.unminded.bee.persistence.mapper;

import cn.unminded.bee.persistence.criteria.QueryRuleCriteria;
import cn.unminded.bee.persistence.entity.RuleRecordEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface RuleMapper {

    List<RuleRecordEntity> list(QueryRuleCriteria criteria);

    Integer insert(RuleRecordEntity entity);

    Integer update(RuleRecordEntity entity);
}
