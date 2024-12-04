package cn.unminded.bee.common.annotation.validator;

import cn.unminded.bee.common.annotation.DictStatus;
import cn.unminded.bee.common.constant.VariableStatusEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author lijunwei
 */
public class DictStatusValidator implements ConstraintValidator<DictStatus, Integer> {

    private boolean required;

    @Override
    public void initialize(DictStatus constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (!this.required && Objects.isNull(value)) {
            return true;
        }

        return VariableStatusEnum.getInstance(value).isPresent();
    }

}