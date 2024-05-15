package cn.unminded.bee.common.config;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.exception.VariableManageException;
import cn.unminded.bee.common.util.BindingResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lijunwei
 */
@Slf4j
@RestControllerAdvice
public class BeeExceptionHandler {

    @ExceptionHandler({Exception.class, Throwable.class})
    public Result exceptionHandler(Throwable e) {
        log.error("系统调用异常: {}", ExceptionUtils.getStackTrace(e));
        return Result.fail("系统内部错误");
    }

    @ExceptionHandler({BindException.class})
    public Result bindExceptionHandler(BindingResult bindingResult) {
        String bindingError = BindingResultUtil.bindingError(bindingResult);
        log.error("参数验证失败: {}", bindingError);
        return Result.fail(bindingError);
    }

    @ExceptionHandler({VariableManageException.class})
    public Result variableManageExceptionHandler(VariableManageException e) {
        log.error("变量管理异常: {}", e.getMessage());
        return Result.fail(e.getMessage());
    }

}
