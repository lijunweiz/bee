package cn.unminded.bee.common.annotation;

import cn.unminded.bee.common.annotation.validator.DictStatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author lijunwei
 */
@Target({METHOD, FIELD, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {DictStatusValidator.class})
public @interface DictStatus {

    boolean required() default true;

    String message() default "字典状态异常";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}