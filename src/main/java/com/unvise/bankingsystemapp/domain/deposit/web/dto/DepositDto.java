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

    @Null(message = "id must be null", groups = {View.New.class})
    private Long id;

    @NotNull(message = "intense_rate must be not null", groups = {View.New.class, View.Update.class})
    @JsonProperty("intense_rate")
    private BigDecimal intenseRate;

    @Null(message = "while creating new deposit balance must be null", groups = {View.New.class})
    @NotNull(message = "while updating deposit balance must not be null", groups = {View.Update.class})
    @PositiveOrZero(message = "while updating deposit balance must be positive or zero", groups = {View.Update.class})
    private BigDecimal balance;

    @NotNull(message = "currency must not be null", groups = {View.New.class, View.Update.class})
    private CurrencyType currency;

    @NotNull(message = "account_history_id must not be null", groups = {View.New.class, View.Update.class})
    @Positive(message = "account_history_id must be positive", groups = {View.New.class, View.Update.class})
    @JsonProperty("account_history_id")
    private Long accountHistoryId;

}
