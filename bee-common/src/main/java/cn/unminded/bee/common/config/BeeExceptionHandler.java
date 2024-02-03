package cn.unminded.bee.common.config;

import cn.unminded.bee.common.Result;
import cn.unminded.bee.common.exception.VariableManageException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lijunwei
 */
@Slf4j
@ControllerAdvice
public class BeeExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e){
        log.error("系统调用异常: {}", ExceptionUtils.getStackTrace(e));
        return Result.fail("系统内部错误");
    }

    @ExceptionHandler({VariableManageException.class})
    @ResponseBody
    public Result variableManageExceptionHandler(VariableManageException e){
        log.error("变量管理异常: {}", e.getMessage());
        return Result.fail(e.getMessage());
    }

}
