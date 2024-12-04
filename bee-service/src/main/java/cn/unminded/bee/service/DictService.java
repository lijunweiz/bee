package cn.unminded.bee.service;

import cn.unminded.bee.persistence.criteria.QueryDictCriteria;
import cn.unminded.bee.persistence.entity.DictEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface DictService {

    Long count(QueryDictCriteria criteria);

    List<DictEntity> query(QueryDictCriteria criteria);

    Integer save(DictEntity dictEntity);

    Integer update(DictEntity dictEntity);
}