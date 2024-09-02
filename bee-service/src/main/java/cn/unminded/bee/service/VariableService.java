package cn.unminded.bee.service;

import cn.unminded.bee.persistence.criteria.QueryVariableCriteria;
import cn.unminded.bee.persistence.entity.VariableEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface VariableService {

    List<VariableEntity> list(QueryVariableCriteria criteria);

    List<VariableEntity> list(String startTime, String endTime, Integer pageSize, boolean asc);

    Long count(QueryVariableCriteria criteria);

    VariableEntity findOne(String variableNameEn);

    VariableEntity findLastOne(String variableNameEn);

    VariableEntity findOneById(Integer variableId);

    boolean save(VariableEntity entity);

    boolean update(VariableEntity entity);

}
