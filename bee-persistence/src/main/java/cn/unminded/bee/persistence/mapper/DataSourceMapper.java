package cn.unminded.bee.persistence.mapper;

import cn.unminded.bee.persistence.criteria.QueryDataSourceCriteria;
import cn.unminded.bee.persistence.entity.DataSourceEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface DataSourceMapper {

    List<DataSourceEntity> list(QueryDataSourceCriteria criteria);

    Integer insert(DataSourceEntity entity);
}
