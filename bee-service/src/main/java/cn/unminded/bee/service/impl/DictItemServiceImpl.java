package cn.unminded.bee.service.impl;

import cn.unminded.bee.persistence.criteria.QueryDictItemCriteria;
import cn.unminded.bee.persistence.entity.DictItemEntity;
import cn.unminded.bee.persistence.mapper.DictItemMapper;
import cn.unminded.bee.service.DictItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author lijunwei
 */
@Slf4j
@Service
public class DictItemServiceImpl implements DictItemService {

    @Resource
    private DictItemMapper dictItemMapper;

    @Override
    public Long count(QueryDictItemCriteria criteria) {
        return dictItemMapper.count(criteria);
    }

    @Override
    public List<DictItemEntity> query(QueryDictItemCriteria criteria) {
        return dictItemMapper.list(criteria);
    }

    @Override
    public Integer save(DictItemEntity dictItemEntity) {
        Objects.requireNonNull(dictItemEntity, "dictItemEntity 不能为null");
        if (Objects.isNull(dictItemEntity.getCreatedTime())) {
            dictItemEntity.setCreatedTime(LocalDateTime.now());
        }
        if (Objects.isNull(dictItemEntity.getUpdatedTime())) {
            dictItemEntity.setUpdatedTime(LocalDateTime.now());
        }

        return dictItemMapper.insert(dictItemEntity);
    }

    @Override
    public Integer update(DictItemEntity dictItemEntity) {
        Objects.requireNonNull(dictItemEntity, "dictItemEntity 不能为null");
        if (Objects.isNull(dictItemEntity.getUpdatedTime())) {
            dictItemEntity.setUpdatedTime(LocalDateTime.now());
        }

        return dictItemMapper.update(dictItemEntity);
    }
}
