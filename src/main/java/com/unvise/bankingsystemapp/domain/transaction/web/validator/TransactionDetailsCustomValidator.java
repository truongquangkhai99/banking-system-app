package com.unvise.bankingsystemapp.domain.transaction.web.validator;

import com.unvise.bankingsystemapp.domain.transaction.enums.TransactionType;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDetailsDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TransactionDetailsCustomValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return TransactionDetailsDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        TransactionDetailsDto transactionDetails = (TransactionDetailsDto) target;

        if (transactionDetails.getTransactionType() == TransactionType.CREDIT
                && transactionDetails.getCreditId() == null) {
            errors.rejectValue("creditId", "creditId.error", "with transaction type" +
                    " 'credit' credit id must not be null");
        }

    }
}
