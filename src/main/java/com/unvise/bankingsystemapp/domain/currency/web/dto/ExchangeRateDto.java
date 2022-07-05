package com.unvise.bankingsystemapp.domain.currency.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.domain.currency.web.validator.FieldUnmatched;
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
        first = "fromCurrency",
        second = "toCurrency",
        message = "The fromCurrency field must not be equal toCurrency field",
        groups = {View.New.class, View.Update.class}
)
public class ExchangeRateDto {

    @Null(groups = {View.New.class, View.Update.class})
    private Long id;

    @NotNull(groups = {View.New.class, View.Update.class})
    @JsonProperty("from_currency")
    private CurrencyType fromCurrency;

    @NotNull(groups = {View.New.class, View.Update.class})
    @JsonProperty("to_currency")
    private CurrencyType toCurrency;

    @NotNull(groups = {View.New.class, View.Update.class})
    @Positive(groups = {View.New.class, View.Update.class})
    private BigDecimal ratio;

}
