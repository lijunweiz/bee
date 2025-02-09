package cn.unminded.bee.service;

import cn.unminded.bee.persistence.criteria.QueryDataSourceCriteria;
import cn.unminded.bee.persistence.entity.DataSourceEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface DataSourceService {

    Long count(QueryDataSourceCriteria criteria);

    List<DataSourceEntity> list(QueryDataSourceCriteria criteria);

    boolean save(DataSourceEntity entity);

    boolean updateStatus(Long dataSourceId, Integer status);

    boolean update(DataSourceEntity entity);
}
