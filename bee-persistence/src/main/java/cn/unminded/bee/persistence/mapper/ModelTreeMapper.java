package cn.unminded.bee.persistence.mapper;

import cn.unminded.bee.persistence.criteria.QueryModelCriteria;
import cn.unminded.bee.persistence.entity.ModelTreeEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface ModelTreeMapper {

    Long count(QueryModelCriteria criteria);

    List<ModelTreeEntity> list(QueryModelCriteria criteria);

    Integer insert(ModelTreeEntity entity);

    Integer update(ModelTreeEntity entity);
}
