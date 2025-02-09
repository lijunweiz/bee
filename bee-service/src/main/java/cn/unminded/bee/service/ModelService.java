package cn.unminded.bee.service;

import cn.unminded.bee.persistence.criteria.DeleteModelCriteria;
import cn.unminded.bee.persistence.criteria.QueryModelCriteria;
import cn.unminded.bee.persistence.entity.ModelTreeEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface ModelService {

    List<ModelTreeEntity> modelTreeData(QueryModelCriteria criteria);

    Long count(QueryModelCriteria criteria);

    Integer save(ModelTreeEntity modelTreeEntity);

    Integer update(ModelTreeEntity modelTreeEntity);

    void delete(DeleteModelCriteria criteria);

}
