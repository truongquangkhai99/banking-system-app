package com.unvise.bankingsystemapp.domain.transaction.web.validator;

import com.unvise.bankingsystemapp.domain.transaction.enums.TransactionType;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TransactionValidator implements ConstraintValidator<ValidateTransaction, TransactionDto> {

    @Override
    public void initialize(ValidateTransaction constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TransactionDto transactionDto, ConstraintValidatorContext constraintValidatorContext) {
        boolean check = true;
        if (transactionDto.getTransactionDetails().getTransactionType() == TransactionType.CREDIT) {
            check = transactionDto.getTransactionDetails().getCreditId() != null;
        }
        if (transactionDto.getTransactionDetails().getTransactionType() == TransactionType.TRANSFER) {
            check = transactionDto.getTransactionDetails().getToAccountId() != null;
        }
        return check;

    }

}
