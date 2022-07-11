package com.unvise.bankingsystemapp.domain.auth.web.validator;

import com.unvise.bankingsystemapp.domain.currency.web.validator.FieldUnmatched;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchedFieldValidator implements ConstraintValidator<MatchedField, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(MatchedField constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            BeanWrapperImpl beanWrapper = new BeanWrapperImpl(o);
            Object firstObj = beanWrapper.getPropertyValue(firstFieldName);
            Object secondObj = beanWrapper.getPropertyValue(secondFieldName);

            valid = firstObj == null
                    && secondObj == null
                    || firstObj != null
                    && firstObj.equals(secondObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
