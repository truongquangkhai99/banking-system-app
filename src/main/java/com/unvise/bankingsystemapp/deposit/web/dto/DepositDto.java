package com.unvise.bankingsystemapp.deposit.web.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositDto {

    @Null(groups = {View.New.class})
    @JsonView({View.Details.class})
    private Long id;

    @NotNull(groups = {View.New.class, View.Update.class})
    @JsonView({View.Details.class})
    private BigDecimal intenseRate;

    @Null(groups = {View.New.class})
    @NotNull(groups = {View.Update.class})
    @PositiveOrZero(groups = {View.Update.class})
    @JsonView({View.Details.class})
    private BigDecimal balance;

    @NotNull(groups = {View.New.class, View.Update.class})
    @JsonView({View.Details.class})
    private CurrencyType currency;

    @NotNull(groups = {View.New.class, View.Update.class})
    private Long accountHistoryId;

}
