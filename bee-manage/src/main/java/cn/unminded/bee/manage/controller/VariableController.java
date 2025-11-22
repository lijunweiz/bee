package cn.unminded.bee.manage.controller;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.common.constant.BeeConstant;
import cn.unminded.bee.common.constant.VariableStatusEnum;
import cn.unminded.bee.common.util.BindingResultUtil;
import cn.unminded.bee.manage.dto.variable.request.AddVariableRequest;
import cn.unminded.bee.manage.dto.variable.request.ModifyVariableStatusRequest;
import cn.unminded.bee.manage.dto.variable.request.UpdateVariableRequest;
import cn.unminded.bee.persistence.criteria.QueryVariableCriteria;
import cn.unminded.bee.persistence.entity.VariableEntity;
import cn.unminded.bee.service.VariableService;
import com.google.common.base.Strings;
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

/**
 * 变量管理
 * @author lijunwei
 */
@Log("变量管理")
@Slf4j
@RequestMapping("/manage/variable")
@RestController
public class VariableController {

    @Resource
    private VariableService variableService;

    @GetMapping("/list")
    public Result queryList(@RequestParam(value = "variableNameEn", required = false) String variableNameEn,
                            @RequestParam(value = "dataSourceName", required = false) String dataSourceName,
                            @RequestParam(value = "dataSourceType", required = false) String dataSourceType,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        QueryVariableCriteria criteria = new QueryVariableCriteria()
                .setVariableNameEn(variableNameEn)
                .setDataSourceName(dataSourceName)
                .setDataSourceType(dataSourceType)
                .setStart(Objects.equals(page, 1) ? 0 : (page - 1) * limit)
                .setLimit(limit);
        List<VariableEntity> list = variableService.list(criteria);
        Map<String, Object> data = new HashMap<>();
        data.put("items", list);
        data.put("total", variableService.count(criteria));
        return Result.ok(data);
    }

    @PostMapping("/add")
    public Result addVariable(@Validated @RequestBody AddVariableRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.fail(BindingResultUtil.bindingError(bindingResult));
        }
        VariableEntity one = variableService.findOne(request.getVariableNameEn());
        if (Objects.nonNull(one)) {
            return Result.fail(String.format("[%s]变量已存在", request.getVariableNameEn()));
        }

        VariableEntity entity = new VariableEntity()
                .setVariableNameEn(request.getVariableNameEn())
                .setVariableNameZh(request.getVariableNameZh())
                .setVariableDesc(request.getVariableDesc())
                .setDataSourceType(request.getDataSourceType())
                .setDataSourceName(request.getDataSourceName())
                .setVariableStatus(VariableStatusEnum.DEFAULT.getStatus())
                .setVersion(BeeConstant.ZERO)
                .setAuthor(request.getAuthor())
                .setRequirementName(request.getRequirementName())
                .setCreatedTime(LocalDateTime.now())
                .setUpdatedTime(LocalDateTime.now());
        boolean save = variableService.save(entity);
        return save ? Result.ok() : Result.fail();
    }

    @PostMapping("/update")
    public Result updateVariable(@Validated @RequestBody UpdateVariableRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.fail(BindingResultUtil.bindingError(bindingResult));
        }
        VariableEntity one = variableService.findLastOne(request.getVariableNameEn());
        if (Objects.isNull(one)) {
            return Result.fail(String.format("[%s]变量不存在, 无需更新", request.getVariableNameEn()));
        }

        VariableEntity entity = new VariableEntity()
                .setVariableNameEn(request.getVariableNameEn())
                .setVariableDesc(request.getVariableDesc())
                .setDataSourceType(Strings.nullToEmpty(request.getDataSourceType()))
                .setDataSourceName(request.getDataSourceName())
                .setVariableStatus(request.getVariableStatus())
                .setVersion(request.getVersion())
                .setAuthor(request.getAuthor())
                .setRequirementName(request.getRequirementName())
                .setUpdatedTime(LocalDateTime.now());
        boolean update = variableService.update(entity);
        return update ? Result.ok() : Result.fail();
    }

    @PostMapping("/modifyStatus")
    public Result modifyStatus(@Validated @RequestBody ModifyVariableStatusRequest request) {
        VariableEntity entity = new VariableEntity()
                .setVariableNameEn(request.getVariableNameEn())
                .setAuthor(request.getAuthor())
                .setVariableStatus(request.getVariableStatus())
                .setUpdatedTime(LocalDateTime.now());
        boolean update = variableService.update(entity);
        return update ? Result.ok() : Result.fail();
    }

}
