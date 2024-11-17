package cn.unminded.bee.common.annotation.validator;

import cn.unminded.bee.common.annotation.VariableStatus;
import cn.unminded.bee.common.constant.VariableStatusEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author lijunwei
 */
public class VariableStatusValidator implements ConstraintValidator<VariableStatus, Integer> {

    private boolean required;

    @Override
    public void initialize(VariableStatus constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (!this.required) {
            return true;
        }

        return VariableStatusEnum.getInstance(value).isPresent();
    }

}
