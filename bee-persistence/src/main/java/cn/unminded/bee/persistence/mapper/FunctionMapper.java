package cn.unminded.bee.persistence.mapper;

import cn.unminded.bee.persistence.criteria.QueryFunctionCriteria;
import cn.unminded.bee.persistence.entity.FunctionEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface FunctionMapper {

    Long count(QueryFunctionCriteria criteria);

    List<FunctionEntity> list(QueryFunctionCriteria criteria);

    Integer insert(FunctionEntity entity);

    void update(FunctionEntity entity);
}
