package cn.unminded.bee.service.impl;

import cn.unminded.bee.common.constant.ModelTreeNodeTypeEnum;
import cn.unminded.bee.common.exception.BeeException;
import cn.unminded.bee.persistence.criteria.DeleteModelCriteria;
import cn.unminded.bee.persistence.criteria.QueryModelCriteria;
import cn.unminded.bee.persistence.entity.ModelEntity;
import cn.unminded.bee.persistence.mapper.ModelMapper;
import cn.unminded.bee.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static cn.unminded.bee.common.constant.BeeConstant.YES;

/**
 * @author lijunwei
 */
@Service
@Slf4j
public class ModelServiceImpl implements ModelService {

    @Resource
    private ModelMapper modelMapper;

    @Override
    public List<ModelEntity> modelTreeData(QueryModelCriteria criteria) {
        List<ModelEntity> list = modelMapper.list(criteria);
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        }

        return Collections.emptyList();
    }

    @Override
    public Long count(QueryModelCriteria criteria) {
        return modelMapper.count(criteria);
    }

    @Override
    public Integer save(ModelEntity modelEntity) {
        if (Objects.isNull(modelEntity.getCreatedTime())) {
            modelEntity.setCreatedTime(LocalDateTime.now());
        }
        if (Objects.isNull(modelEntity.getUpdatedTime())) {
            modelEntity.setUpdatedTime(LocalDateTime.now());
        }
        return modelMapper.insert(modelEntity);
    }

    @Override
    public Integer update(ModelEntity modelEntity) {
        Objects.requireNonNull(modelEntity.getId(), "id不能null");
        QueryModelCriteria criteria = new QueryModelCriteria()
                .setId(modelEntity.getId());
        List<ModelEntity> list = modelMapper.list(criteria);
        if (CollectionUtils.isEmpty(list)) {
            throw new BeeException(modelEntity.getId() + "查不到数据");
        }

        ModelEntity entity = list.get(0);
        if (!Objects.equals(modelEntity.getModelType(), entity.getModelType())
                || !Objects.equals(modelEntity.getModelName(), entity.getModelName())
                || !Objects.equals(modelEntity.getIsLeaf(), entity.getIsLeaf())
                || !Objects.equals(modelEntity.getModelDesc(), entity.getModelDesc())) {
            modelEntity.setUpdatedTime(LocalDateTime.now());
        } else {
            log.info("{}数据无变化, 直接返回更新成功", modelEntity.getId());
            return YES;
        }

        return modelMapper.update(modelEntity);
    }

    @Override
    public void delete(DeleteModelCriteria criteria) {
        QueryModelCriteria queryModelCriteria = new QueryModelCriteria()
                .setId(criteria.getId())
                .setIsLeaf(criteria.getIsLeaf());
        List<ModelEntity> list = modelMapper.list(queryModelCriteria);
        if (CollectionUtils.isEmpty(list)) {
            throw new BeeException("没有需要删除的数据，请检查");
        }
        // 如果是非叶子节点
        if (Objects.equals(criteria.getIsLeaf(), ModelTreeNodeTypeEnum.NO.getCode())) {
            throw new BeeException("请先删除子节点");
        } else { // 如果是叶子节点
            ModelEntity modelEntity = list.get(0);
            //todo
        }
    }
}
