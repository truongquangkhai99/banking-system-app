package com.unvise.bankingsystemapp.currency.web.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
@FieldUnmatched(first = "fromCurrency", second = "toCurrency", groups = ExchangeRateViews.New.class)
public class ExchangeRateDto {

    @Null(groups = {ExchangeRateViews.New.class})
    @JsonView(ExchangeRateViews.Details.class)
    private Long id;

    @NotNull(groups = {ExchangeRateViews.New.class})
    @JsonView(ExchangeRateViews.Details.class)
    private CurrencyType fromCurrency;

    @NotNull(groups = {ExchangeRateViews.New.class})
    @JsonView(ExchangeRateViews.Details.class)
    private CurrencyType toCurrency;

    @NotNull(groups = {ExchangeRateViews.New.class})
    @Positive(groups = {ExchangeRateViews.New.class})
    @JsonView(ExchangeRateViews.Details.class)
    private BigDecimal ratio;

}
