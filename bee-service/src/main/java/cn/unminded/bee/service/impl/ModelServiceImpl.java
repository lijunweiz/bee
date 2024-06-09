package cn.unminded.bee.service.impl;

import cn.unminded.bee.persistence.criteria.QueryModelCriteria;
import cn.unminded.bee.persistence.entity.ModelTreeEntity;
import cn.unminded.bee.persistence.mapper.ModelTreeMapper;
import cn.unminded.bee.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author lijunwei
 */
@Service
@Slf4j
public class ModelServiceImpl implements ModelService {

    @Resource
    private ModelTreeMapper modelTreeMapper;

    @Override
    public List<ModelTreeEntity> modelTreeData(QueryModelCriteria criteria) {
        List<ModelTreeEntity> list = modelTreeMapper.list(criteria);
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        }

        return Collections.emptyList();
    }

    @Override
    public Integer save(ModelTreeEntity modelTreeEntity) {
        return modelTreeMapper.insert(modelTreeEntity);
    }
}
