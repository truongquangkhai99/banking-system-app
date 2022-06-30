package com.unvise.bankingsystemapp.currency.web.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.currency.web.validator.FieldUnmatched;
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
@FieldUnmatched(first = "fromCurrency", second = "toCurrency", groups = {View.New.class, View.Update.class})
public class ExchangeRateDto {

    @Null(groups = {View.New.class})
    @NotNull(groups = {View.Update.class})
    @JsonView(View.Details.class)
    private Long id;

    @NotNull(groups = {View.New.class, View.Update.class})
    @JsonView(View.Details.class)
    private CurrencyType fromCurrency;

    @NotNull(groups = {View.New.class, View.Update.class})
    @JsonView(View.Details.class)
    private CurrencyType toCurrency;

    @NotNull(groups = {View.New.class, View.Update.class})
    @Positive(groups = {View.New.class, View.Update.class})
    @JsonView(View.Details.class)
    private BigDecimal ratio;

}
