package cn.unminded.bee.persistence.mapper;

import cn.unminded.bee.persistence.criteria.QueryVariableCriteria;
import cn.unminded.bee.persistence.entity.VariableEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author lijunwei
 */
public interface VariableMapper {

    Integer connectKeepAlive();

    List<VariableEntity> list(QueryVariableCriteria criteria);

    List<VariableEntity> list(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("pageSize") Integer pageSize, @Param("asc") boolean asc);

    Long count(QueryVariableCriteria criteria);

    VariableEntity findLastOne(@Param("variableNameEn") String variableNameEn);

    VariableEntity findOneById(@Param("id") Integer variableId);

    Integer insert(VariableEntity entity);

    Integer update(VariableEntity entity);

}
