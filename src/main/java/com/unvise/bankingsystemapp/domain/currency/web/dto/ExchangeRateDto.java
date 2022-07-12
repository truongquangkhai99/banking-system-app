package com.unvise.bankingsystemapp.domain.currency.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unvise.bankingsystemapp.domain.common.View;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.domain.common.web.validator.FieldUnmatched;
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
        message = "The from_currency field must not be equal to_currency field",
        groups = {View.New.class, View.Update.class}
)
public class ExchangeRateDto {

    @Null(message = "id must be null", groups = {View.New.class, View.Update.class})
    private Long id;

    @NotNull(message = "from_currency must not be null", groups = {View.New.class, View.Update.class})
    @JsonProperty("from_currency")
    private CurrencyType fromCurrency;

    @NotNull(message = "to_currency must not be null", groups = {View.New.class, View.Update.class})
    @JsonProperty("to_currency")
    private CurrencyType toCurrency;

    @NotNull(message = "ratio must not be null", groups = {View.New.class, View.Update.class})
    @Positive(message = "ratio deposit balance must be positive", groups = {View.New.class, View.Update.class})
    private BigDecimal ratio;

}
