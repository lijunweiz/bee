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

    List<VariableEntity> list(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("pageSize") Integer pageSize, @Param("asc") boolean ascending);

    Long count(QueryVariableCriteria criteria);

    VariableEntity findLastOne(@Param("variableNameEn") String variableNameEn);

    VariableEntity findOneById(@Param("variableId") Integer variableId);

    Integer insert(VariableEntity entity);

    Integer update(VariableEntity entity);

    @Update("update t_variable set dataSourceName = #{dataSourceName}, dataSourceType = #{dataSourceType} where dataSourceId = #{dataSourceId}")
    Integer updateDataSource(@Param("dataSourceId") Long dataSourceId, @Param("dataSourceName") String dataSourceName, @Param("dataSourceType") String dataSourceType);


}
