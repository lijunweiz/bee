package cn.unminded.bee.manage.controller;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.constant.BeeConstant;
import cn.unminded.bee.common.constant.VariableStatusEnum;
import cn.unminded.bee.common.util.BindingResultUtil;
import cn.unminded.bee.manage.dto.variable.request.AddVariableRequest;
import cn.unminded.bee.manage.dto.variable.request.UpdateVariableRequest;
import cn.unminded.bee.persistence.criteria.QueryVariableCriteria;
import cn.unminded.bee.persistence.entity.VariableEntity;
import cn.unminded.bee.service.VariableService;
import cn.unminded.rtool.util.DateUtils;
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
@Slf4j
@RequestMapping("/manage/variable")
@RestController
public class VariableController {

    @Resource
    private VariableService variableService;

    @GetMapping("/list")
    public Result queryList(@RequestParam(value = "variableNameEn", required = false) String variableNameEn,
                            @RequestParam(value = "variableSource", required = false) String variableSource,
                            @RequestParam(value = "variableType", required = false) String variableType) {
        QueryVariableCriteria criteria = new QueryVariableCriteria()
                .setVariableNameEn(variableNameEn)
                .setVariableSource(variableSource)
                .setVariableType(variableType)
                .setStartTime(DateUtils.ymdHms(DateUtils.plusDays(LocalDateTime.now(), -30)));
        List<VariableEntity> list = variableService.list(criteria);
        Map<String, Object> data = new HashMap<>();
        data.put("items", list);
        data.put("total", list.size());
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
                .setVariableSource(request.getVariableSource())
                .setVariableType(request.getVariableAddress())
                .setVariableParam(Objects.isNull(request.getVariableParam()) ? BeeConstant.EMPTY_STRING : request.getVariableParam())
                .setVariableStatus(VariableStatusEnum.FRESH.getStatus())
                .setVariableVersion(BeeConstant.ZERO)
                .setAuthor(request.getAuthor())
                .setVariableAddress(request.getVariableAddress())
                .setRequirementName(request.getRequirementName())
                .setCreatedTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
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
                .setVariableSource(request.getVariableSource())
                .setVariableType(request.getVariableType())
                .setVariableParam(request.getVariableParam())
                .setVariableStatus(request.getVariableStatus())
                .setVariableVersion(request.getVariableVersion())
                .setVariableAddress(request.getVariableAddress())
                .setAuthor(request.getAuthor())
                .setRequirementName(request.getRequirementName())
                .setUpdateTime(LocalDateTime.now());
        boolean update = variableService.update(entity);
        return update ? Result.ok() : Result.fail();
    }

}
