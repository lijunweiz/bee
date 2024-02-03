package cn.unminded.bee.manage.controller;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.constant.DataSourceTypeEnum;
import cn.unminded.bee.common.util.BindingResultUtil;
import cn.unminded.bee.manage.dto.datasource.request.AddDataSourceRequest;
import cn.unminded.bee.persistence.criteria.QueryDataSourceCriteria;
import cn.unminded.bee.persistence.entity.DataSourceEntity;
import cn.unminded.bee.service.DataSourceService;
import cn.unminded.rtool.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lijunwei
 */
@Slf4j
@RequestMapping("/manage/datasource")
@RestController
public class DataSourceController {

    @Resource
    private DataSourceService dataSourceService;

    @GetMapping("/types")
    public Result queryDataSourceType() {
        Map<String, Object> data = new HashMap<>();
        data.put("dataSourceTypeOptions", DataSourceTypeEnum.getDataSourceTypeNames());
        return Result.ok(data);
    }

    @GetMapping("/list")
    public Result query(@RequestParam(value = "dataSourceName", required = false) String dataSourceName) {
        Map<String, Object> data = new HashMap<>();
        QueryDataSourceCriteria criteria = new QueryDataSourceCriteria()
                .setStartTime(DateUtils.ymdHms(DateUtils.plusDays(LocalDateTime.now(), -30)))
                .setDataSourceName(dataSourceName);
        data.put("list", dataSourceService.list(criteria));
        return Result.ok(data);
    }

    @PostMapping("/add")
    public Result add(@Validated @RequestBody AddDataSourceRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.fail(BindingResultUtil.bindingError(bindingResult));
        }
        DataSourceEntity entity = new DataSourceEntity()
                .setDataSourceName(request.getDataSourceName())
                .setDataSourceType(request.getDataSourceType())
                .setStatus(request.getStatus())
                .setDesc(request.getDesc())
                .setProtocol(request.getProtocol())
                .setMethod(request.getMethod())
                .setAddress(request.getAddress())
                .setQueryParam(request.getQueryParam())
                .setHeaders(request.getHeaders())
                .setRequestBody(request.getRequestBody())
                .setExtractVariable(request.getExtractVariable())
                .setCreatedTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        dataSourceService.save(entity);
        return Result.ok();
    }

}
