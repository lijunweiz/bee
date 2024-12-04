package cn.unminded.bee.service.impl;

import cn.unminded.bee.persistence.criteria.QueryDictCriteria;
import cn.unminded.bee.persistence.entity.DictEntity;
import cn.unminded.bee.persistence.mapper.DictMapper;
import cn.unminded.bee.service.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author lijunwei
 */
@Service
public class DictServiceImpl implements DictService {

    @Resource
    private DictMapper dictMapper;

    @Override
    public Long count(QueryDictCriteria criteria) {
        return dictMapper.count(criteria);
    }

    @Override
    public List<DictEntity> query(QueryDictCriteria criteria) {
        return dictMapper.list(criteria);
    }

    @Override
    public Integer save(DictEntity dictEntity) {
        Objects.requireNonNull(dictEntity, "dictEntity 不能为null");
        if (Objects.isNull(dictEntity.getCreatedTime())) {
            dictEntity.setCreatedTime(LocalDateTime.now());
        }
        if (Objects.isNull(dictEntity.getUpdateTime())) {
            dictEntity.setUpdateTime(LocalDateTime.now());
        }

        return dictMapper.insert(dictEntity);
    }

    @Override
    public Integer update(DictEntity dictEntity) {
        Objects.requireNonNull(dictEntity, "dictEntity 不能为null");
        if (Objects.isNull(dictEntity.getUpdateTime())) {
            dictEntity.setUpdateTime(LocalDateTime.now());
        }

        return dictMapper.update(dictEntity);
    }
}