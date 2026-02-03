package cn.unminded.bee.manage.controller.aviator;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.annotation.Log;
import cn.unminded.bee.common.constant.BeeConstant;
import cn.unminded.bee.core.util.BeeCoreExceptionUtil;
import cn.unminded.bee.persistence.entity.FunctionEntity;
import cn.unminded.bee.service.aviator.AviatorService;
import cn.unminded.bee.turn.dto.aviator.request.EvalContentRequest;
import cn.unminded.bee.turn.dto.aviator.request.ModifyFuncStatusRequest;
import cn.unminded.bee.turn.dto.aviator.request.AddAviatorFuncRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author lijunwei
 */
@Log("表达式控制")
@Slf4j
@RequestMapping("/manage/aviator")
@RestController
public class AviatorController {

    @Resource
    private AviatorService aviatorService;

    @Log("函数列表")
    @GetMapping("/funMapList")
    public Result funMapList() {
        return Result.ok(aviatorService.funMapList());
    }

    @Log("添加函数")
    @PostMapping("/addFunc")
    public Result addFunc(@Validated @RequestBody AddAviatorFuncRequest func) {
        aviatorService.addFunc(func);
        return Result.ok();
    }

    @Log("更新函数状态")
    @PostMapping("/updateFunc")
    public Result updateFunc(@Validated @RequestBody ModifyFuncStatusRequest modifyFuncStatusRequest) {
        boolean valid = Objects.isNull(modifyFuncStatusRequest.getId())
                && (StringUtils.isBlank(modifyFuncStatusRequest.getFuncNameEn()) && Objects.isNull(modifyFuncStatusRequest.getVersion()));
        BeeCoreExceptionUtil.trueToThrow(valid, "参数有误");
        FunctionEntity functionEntity = new FunctionEntity()
                .setId(modifyFuncStatusRequest.getId())
                .setFuncNameEn(modifyFuncStatusRequest.getFuncNameEn())
                .setVersion(modifyFuncStatusRequest.getVersion())
                .setStatus(BeeConstant.FUNCTION_STATUS1)
                .setUpdatedTime(LocalDateTime.now())
                ;
        aviatorService.updateFunc(functionEntity);
        return Result.ok();
    }

    @Log("表达式计算")
    @PostMapping("/eval")
    public Result evalExpression(@Validated @RequestBody EvalContentRequest evalContentRequest) {
        return Result.ok(aviatorService.eval(evalContentRequest.getExpression(), evalContentRequest.getContext()));
    }

}
