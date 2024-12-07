package cn.unminded.bee.service.impl;

import cn.unminded.bee.common.constant.BeeConstant;
import cn.unminded.bee.common.exception.VariableManageException;
import cn.unminded.bee.persistence.criteria.QueryDataSourceCriteria;
import cn.unminded.bee.persistence.entity.DataSourceEntity;
import cn.unminded.bee.persistence.mapper.DataSourceMapper;
import cn.unminded.bee.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class DataSourceServiceImpl implements DataSourceService {

    @Resource
    private DataSourceMapper dataSourceMapper;

    @Override
    public List<DataSourceEntity> list(QueryDataSourceCriteria criteria) {
        if (StringUtils.isBlank(criteria.getStartTime())) {
            criteria.setStartTime(BeeConstant.BEE_START_TIME);
        }
        if (StringUtils.isBlank(criteria.getStartTime()) && StringUtils.isBlank(criteria.getDataSourceName())) {
            throw new VariableManageException("查询开始时间和数据源名称不能同时为空");
        }
        return dataSourceMapper.list(criteria);
    }

    @Override
    public boolean save(DataSourceEntity entity) {
        return dataSourceMapper.insert(entity) == 1;
    }

    @Override
    public boolean updateStatus(Long dataSourceId, Integer status) {
        DataSourceEntity entity = new DataSourceEntity()
                .setDataSourceId(dataSourceId)
                .setDataSourceStatus(status)
                .setUpdateTime(LocalDateTime.now());
        return dataSourceMapper.update(entity) == 1;
    }

    @Override
    public boolean update(DataSourceEntity entity) {
        Objects.requireNonNull(entity);
        Objects.requireNonNull(entity.getDataSourceId());
        if (Objects.isNull(entity.getUpdateTime())) {
            entity.setUpdateTime(LocalDateTime.now());
        }

        return dataSourceMapper.update(entity) == 1;
    }
}
