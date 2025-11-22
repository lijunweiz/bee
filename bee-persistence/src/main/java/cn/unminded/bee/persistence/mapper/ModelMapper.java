package cn.unminded.bee.persistence.mapper;

import cn.unminded.bee.persistence.criteria.QueryModelCriteria;
import cn.unminded.bee.persistence.entity.ModelEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface ModelMapper {

    Long count(QueryModelCriteria criteria);

    List<ModelEntity> list(QueryModelCriteria criteria);

    Integer insert(ModelEntity entity);

    Integer update(ModelEntity entity);
}
