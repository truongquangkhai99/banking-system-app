package com.unvise.bankingsystemapp.domain.credit.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.domain.credit.web.validator.ValidateCredit;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidateCredit(groups = {View.Update.class})
public class CreditDto {

    @Null(message = "id must be null", groups = {View.New.class, View.Update.class})
    private Long id;

    @NotNull(message = "total must be not null", groups = {View.New.class, View.Update.class})
    @Positive(message = "total must be positive", groups = {View.New.class})
    private BigDecimal total;

    @Null(message ="while creating new credit, current must be null", groups = {View.New.class})
    @NotNull(message ="while updating credit, current must not be null", groups = {View.Update.class})
    @Positive(message ="while updating credit, current must be positive", groups = {View.Update.class})
    private BigDecimal current;

    @Null(message ="while creating new credit, remain must be null", groups = {View.New.class})
    @NotNull(message ="while updating credit, remain must not be null", groups = {View.Update.class})
    @PositiveOrZero(message ="while updating credit, remain must be positive or zero", groups = {View.Update.class})
    private BigDecimal remain;

    @NotNull(message ="date_between_payments_in_days must not be null", groups = {View.New.class, View.Update.class})
    @JsonProperty("date_between_payments_in_days")
    private Integer dateBetweenPaymentsInDays;

    @NotNull(message = "currency must not be null", groups = {View.New.class, View.Update.class})
    private CurrencyType currency;

    @Null(message = "while creating new credit, is_closed must be null", groups = {View.New.class})
    @NotNull(message ="while updating credit, is_closed must not be null", groups = {View.Update.class})
    @JsonProperty("is_closed")
    private Boolean isClosed;

    @NotNull(message = "account_history_id must not be null", groups = {View.New.class, View.Update.class})
    @Positive(message ="account_history_id must be positive", groups = {View.New.class, View.Update.class})
    @JsonProperty("account_history_id")
    private Long accountHistoryId;

}
