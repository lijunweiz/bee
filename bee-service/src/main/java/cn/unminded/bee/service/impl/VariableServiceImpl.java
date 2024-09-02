package cn.unminded.bee.service.impl;

import cn.unminded.bee.common.constant.BeeConstant;
import cn.unminded.bee.common.exception.VariableManageException;
import cn.unminded.bee.persistence.criteria.QueryVariableCriteria;
import cn.unminded.bee.persistence.entity.VariableEntity;
import cn.unminded.bee.persistence.mapper.VariableMapper;
import cn.unminded.bee.service.VariableService;
import cn.unminded.rtool.util.DateUtils;
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
public class VariableServiceImpl implements VariableService {

    @Resource
    private VariableMapper variableMapper;

    @Override
    public List<VariableEntity> list(QueryVariableCriteria criteria) {
        if (StringUtils.isBlank(criteria.getStartTime())) {
            criteria.setStartTime(BeeConstant.BEE_START_TIME);
        }
        if (StringUtils.isBlank(criteria.getStartTime()) && StringUtils.isBlank(criteria.getVariableNameEn())) {
            throw new VariableManageException("查询开始时间和变量英文不能同时为空");
        }
        return variableMapper.list(criteria);
    }

    @Override
    public List<VariableEntity> list(String startTime, String endTime, Integer pageSize, boolean asc) {
        if (StringUtils.isBlank(startTime)) {
            startTime = BeeConstant.BEE_START_TIME;
        }
        if (StringUtils.isBlank(endTime)) {
            endTime = DateUtils.ymdHms();
        }
        if (Objects.isNull(pageSize) || pageSize > 20) {
            pageSize = 20;
        }
        return variableMapper.list(startTime, endTime, pageSize, asc);
    }

    @Override
    public Long count(QueryVariableCriteria criteria) {
        return variableMapper.count(criteria);
    }

    @Override
    public VariableEntity findOne(String variableNameEn) {
        return variableMapper.findLastOne(variableNameEn);
    }

    @Override
    public VariableEntity findLastOne(String variableNameEn) {
        return variableMapper.findLastOne(variableNameEn);
    }

    @Override
    public VariableEntity findOneById(Integer variableId) {
        return variableMapper.findOneById(variableId);
    }

    @Override
    public boolean save(VariableEntity entity) {
        return variableMapper.insert(entity) == 1;
    }

    @Override
    public boolean update(VariableEntity entity) {
        return variableMapper.update(entity) == 1;
    }
}
