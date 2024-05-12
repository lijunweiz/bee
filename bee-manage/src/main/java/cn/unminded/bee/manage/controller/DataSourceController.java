package cn.unminded.bee.manage.controller;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.common.constant.DataSourceStatusEnum;
import cn.unminded.bee.common.constant.DataSourceTypeEnum;
import cn.unminded.bee.common.util.BindingResultUtil;
import cn.unminded.bee.manage.dto.datasource.request.AddDataSourceRequest;
import cn.unminded.bee.manage.dto.datasource.request.ModifyDataSourceRequest;
import cn.unminded.bee.persistence.criteria.QueryDataSourceCriteria;
import cn.unminded.bee.persistence.entity.DataSourceEntity;
import cn.unminded.bee.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lijunwei
 */
@Log("数据源配置")
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

    @GetMapping("/names")
    public Result queryDataSourceName() {
        Map<String, Object> data = new HashMap<>();
        List<String> dsNames = dataSourceService.list(new QueryDataSourceCriteria()).stream().map(DataSourceEntity::getDataSourceName).collect(Collectors.toList());
        data.put("dataSourceNameOptions", dsNames);
        return Result.ok(data);
    }

    @GetMapping("/list")
    public Result query(@RequestParam(value = "dataSourceName", required = false) String dataSourceName) {
        Map<String, Object> data = new HashMap<>();
        QueryDataSourceCriteria criteria = new QueryDataSourceCriteria()
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

    /**
     * todo
     * @param request
     * @param bindingResult
     * @return
     */
    @PostMapping("/update")
    public Result update(@Validated @RequestBody AddDataSourceRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.fail(BindingResultUtil.bindingError(bindingResult));
        }
        return Result.ok();
    }

    /**
     * 发布数据源
     * @param request 修改数据源的参数，发布数据源需要数据源id和要发布时的状态
     * @param bindingResult
     * @return
     */
    @PostMapping("/publish")
    public Result publish(@Validated @RequestBody ModifyDataSourceRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.fail(BindingResultUtil.bindingError(bindingResult));
        }
        if (!Objects.equals(request.getStatus(), DataSourceStatusEnum.RUNNING.getStatus())) {
            return Result.fail("发布状态有误");
        }

        dataSourceService.updateStatus(request.getDataSourceId(), request.getStatus());
        return Result.ok();
    }

}
