package com.unvise.bankingsystemapp.transaction.web.dto;

import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.currency.web.validator.FieldUnmatched;
import com.unvise.bankingsystemapp.transaction.enums.TransactionType;
import com.unvise.bankingsystemapp.transaction.web.validator.CanBeNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldUnmatched(
        message = "The fromAccountId field must not be equals toAccountId",
        first = "fromAccountId",
        second = "toAccountId",
        groups = {View.New.class}
)
public class TransactionDetailsDto {

    @Null(groups = {View.New.class})
    private Long id;

    @NotNull(groups = {View.New.class})
    @Positive(groups = {View.New.class})
    private BigDecimal amount;

    @NotNull(groups = {View.New.class})
    private TransactionType transactionType;

    @NotNull(groups = {View.New.class})
    private Long fromAccountId;

    @CanBeNull(groups = {View.New.class})
    private Long toAccountId;

    @CanBeNull(groups = {View.New.class})
    private Long creditId;

}
