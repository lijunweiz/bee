package cn.unminded.bee.common.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Objects;

/**
 * spring注解验证错误收集工具
 * @author lijunwei
 */
public class BindingResultUtil {

    private BindingResultUtil() {
        throw new UnsupportedOperationException();
    }

    public static String bindingError(BindingResult bindingResult) {
        Objects.requireNonNull(bindingResult, "bindingResult must not be null");

        StringBuilder sb = new StringBuilder();
        if (bindingResult.hasErrors()) {
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                sb.append(objectError.getDefaultMessage()).append(",");
            }
        }
        String error = sb.toString();
        return error.endsWith(",") ? error.substring(0, error.length() - 1) : error;
    }

}
