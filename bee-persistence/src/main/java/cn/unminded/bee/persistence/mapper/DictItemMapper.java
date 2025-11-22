package cn.unminded.bee.persistence.mapper;

import cn.unminded.bee.persistence.criteria.QueryDictItemCriteria;
import cn.unminded.bee.persistence.entity.DictItemEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface DictItemMapper {

    Long count(QueryDictItemCriteria criteria);

    List<DictItemEntity> list(QueryDictItemCriteria criteria);

    Integer insert(DictItemEntity entity);

    Integer update(DictItemEntity entity);

}