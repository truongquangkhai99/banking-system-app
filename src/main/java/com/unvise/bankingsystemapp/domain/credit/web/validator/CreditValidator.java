package com.unvise.bankingsystemapp.domain.credit.web.validator;

import com.unvise.bankingsystemapp.domain.credit.web.dto.CreditDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreditValidator implements ConstraintValidator<ValidateCredit, CreditDto> {

    @Override
    public void initialize(ValidateCredit constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CreditDto creditDto, ConstraintValidatorContext constraintValidatorContext) {
        if (creditDto.getCurrent() != null
                && creditDto.getRemain() != null
                && creditDto.getIsClosed() != null) {
            boolean check = creditDto.getTotal().equals(creditDto.getCurrent().add(creditDto.getRemain()));

            if (creditDto.getIsClosed()) {
                return check && creditDto.getTotal().compareTo(creditDto.getCurrent()) == 0;
            }

            return check;
        }
        return true;
    }

}
