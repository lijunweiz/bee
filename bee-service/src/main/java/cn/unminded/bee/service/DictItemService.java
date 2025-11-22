package cn.unminded.bee.service;

import cn.unminded.bee.persistence.criteria.QueryDictItemCriteria;
import cn.unminded.bee.persistence.entity.DictItemEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface DictItemService {

    Long count(QueryDictItemCriteria criteria);

    List<DictItemEntity> query(QueryDictItemCriteria criteria);

    Integer save(DictItemEntity dictItemEntity);

    Integer update(DictItemEntity dictItemEntity);
}