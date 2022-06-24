package com.unvise.bankingsystemapp.currency.web.validator;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldUnmatchedValidator implements ConstraintValidator<FieldUnmatched, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;
    
    @Override
    public void initialize(FieldUnmatched constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
            Object firstObj = beanWrapper.getPropertyValue(firstFieldName);
            Object secondObj = beanWrapper.getPropertyValue(secondFieldName);

            valid = firstObj == null
                    && secondObj == null
                    || firstObj != null
                    && !firstObj.equals(secondObj);
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
