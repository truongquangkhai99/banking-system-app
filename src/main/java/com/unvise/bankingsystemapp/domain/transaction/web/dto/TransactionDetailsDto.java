package com.unvise.bankingsystemapp.domain.transaction.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unvise.bankingsystemapp.domain.common.View;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.domain.currency.web.validator.FieldUnmatched;
import com.unvise.bankingsystemapp.domain.transaction.enums.TransactionType;
import com.unvise.bankingsystemapp.domain.transaction.web.validator.CanBeNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldUnmatched(
        message = "from account id field must not be equals to account id",
        first = "fromAccountId",
        second = "toAccountId",
        groups = {View.New.class}
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDetailsDto {

    @Null(message = "id must be null", groups = {View.New.class})
    private Long id;

    @NotNull(message = "amount must be not null", groups = {View.New.class})
    @Positive(message = "amount must be a positive number greater than zero", groups = {View.New.class})
    @Max(value = 1000000000, message = "amount must be lower than billion", groups = {View.New.class})
    private BigDecimal amount;

    @NotNull(message = "transaction_type must be not null and have valid type", groups = {View.New.class})
    @JsonProperty("transaction_type")
    private TransactionType transactionType;

    @NotNull(message = "from_account_id must be not null", groups = {View.New.class})
    @Positive(message = "from_account_id must be a positive number greater than zero", groups = {View.New.class})
    @JsonProperty("from_account_id")
    private Long fromAccountId;

    @CanBeNull(groups = {View.New.class})
    private CurrencyType currency;

    @CanBeNull(groups = {View.New.class})
    @Positive(message = "to_account_id must be a positive number greater than zero", groups = {View.New.class})
    @JsonProperty("to_account_id")
    private Long toAccountId;

    @CanBeNull(groups = {View.New.class})
    @Positive(message = "credit_id must be a positive number greater than zero", groups = {View.New.class})
    @JsonProperty("credit_id")
    private Long creditId;

}
