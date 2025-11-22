package cn.unminded.bee.service;

import cn.unminded.bee.persistence.criteria.DeleteModelCriteria;
import cn.unminded.bee.persistence.criteria.QueryModelCriteria;
import cn.unminded.bee.persistence.entity.ModelEntity;

import java.util.List;

/**
 * @author lijunwei
 */
public interface ModelService {

    List<ModelEntity> modelTreeData(QueryModelCriteria criteria);

    Long count(QueryModelCriteria criteria);

    Integer save(ModelEntity modelEntity);

    Integer update(ModelEntity modelEntity);

    void delete(DeleteModelCriteria criteria);

}
