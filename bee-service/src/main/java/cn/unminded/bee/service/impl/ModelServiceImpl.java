package cn.unminded.bee.service.impl;

import cn.unminded.bee.common.exception.BeeException;
import cn.unminded.bee.persistence.criteria.QueryModelCriteria;
import cn.unminded.bee.persistence.entity.ModelTreeEntity;
import cn.unminded.bee.persistence.mapper.ModelTreeMapper;
import cn.unminded.bee.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        if (Objects.isNull(modelTreeEntity.getCreatedTime())) {
            modelTreeEntity.setCreatedTime(LocalDateTime.now());
        }
        if (Objects.isNull(modelTreeEntity.getUpdateTime())) {
            modelTreeEntity.setUpdateTime(LocalDateTime.now());
        }
        return modelTreeMapper.insert(modelTreeEntity);
    }

    @Override
    public Integer update(ModelTreeEntity modelTreeEntity) {
        Objects.requireNonNull(modelTreeEntity.getModelId(), "modelId 不能null");
        QueryModelCriteria criteria = new QueryModelCriteria()
                .setModelId(modelTreeEntity.getModelId());
        List<ModelTreeEntity> list = modelTreeMapper.list(criteria);
        if (CollectionUtils.isEmpty(list)) {
            throw new BeeException(modelTreeEntity.getModelId() + "查不到数据");
        }

        ModelTreeEntity entity = list.get(0);
        if (!Objects.equals(modelTreeEntity.getModelType(), entity.getModelType())
                || !Objects.equals(modelTreeEntity.getModelName(), entity.getModelName())
                || !Objects.equals(modelTreeEntity.getIsLeaf(), entity.getIsLeaf())
                || !Objects.equals(modelTreeEntity.getModelDesc(), entity.getModelDesc())) {
            modelTreeEntity.setUpdateTime(LocalDateTime.now());
        }

        return modelTreeMapper.update(modelTreeEntity);
    }
}
