package com.unvise.bankingsystemapp.domain.deposit.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unvise.bankingsystemapp.common.View;
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
public class DepositDto {

    @Null(groups = {View.New.class})
    private Long id;

    @NotNull(groups = {View.New.class, View.Update.class})
    @JsonProperty("intense_rate")
    private BigDecimal intenseRate;

    @Null(groups = {View.New.class})
    @NotNull(groups = {View.Update.class})
    @PositiveOrZero(groups = {View.Update.class})
    private BigDecimal balance;

    @NotNull(groups = {View.New.class, View.Update.class})
    private CurrencyType currency;

    @NotNull(groups = {View.New.class, View.Update.class})
    @Positive(groups = {View.New.class, View.Update.class})
    @JsonProperty("account_history_id")
    private Long accountHistoryId;

}
