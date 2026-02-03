package cn.unminded.bee.service.impl;

import cn.unminded.bee.common.exception.BeeException;
import cn.unminded.bee.persistence.criteria.QueryRuleCriteria;
import cn.unminded.bee.persistence.entity.RuleRecordEntity;
import cn.unminded.bee.persistence.mapper.RuleMapper;
import cn.unminded.bee.service.RuleOperationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author lijunwei
 */
@Slf4j
@Service
public class RuleOperationServiceImpl implements RuleOperationService {

    @Resource
    private RuleMapper ruleMapper;

    @Override
    public List<RuleRecordEntity> query(QueryRuleCriteria criteria) {
        if (Objects.isNull(criteria)) {
            throw new BeeException("查询条件不能为空");
        }
        if (Objects.isNull(criteria.getId())
                && Objects.isNull(criteria.getModelId())
                && Objects.isNull(criteria.getRuleGroup())
                && StringUtils.isBlank(criteria.getRuleName())) {
            throw new BeeException("查询条件至少一个不为空");
        }
        return ruleMapper.list(criteria);
    }

    @Override
    public Integer save(RuleRecordEntity ruleRecordEntity) {
        return ruleMapper.insert(ruleRecordEntity);
    }

    @Override
    public Integer update(RuleRecordEntity ruleRecordEntity) {
        return ruleMapper.update(ruleRecordEntity);
    }

}
