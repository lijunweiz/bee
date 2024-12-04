package cn.unminded.bee.persistence.mapper;

import cn.unminded.bee.persistence.criteria.QueryDictCriteria;
import cn.unminded.bee.persistence.entity.DictEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface DictMapper {

    Long count(QueryDictCriteria criteria);

    List<DictEntity> list(QueryDictCriteria criteria);

    Integer insert(DictEntity entity);

    Integer update(DictEntity entity);

}