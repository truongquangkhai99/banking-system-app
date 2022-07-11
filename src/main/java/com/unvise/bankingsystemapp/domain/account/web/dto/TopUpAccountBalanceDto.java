package com.unvise.bankingsystemapp.domain.account.web.dto;

import com.unvise.bankingsystemapp.domain.common.View;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopUpAccountBalanceDto {

    @NotNull(message = "currency must not be null", groups = View.New.class)
    private CurrencyType currency;

    @NotNull(message = "amount must not be null", groups = View.New.class)
    @DecimalMin(message = "amount must be greater or equals 1", value = "1", groups = View.New.class)
    @DecimalMax(message = "amount must be lower or equals 1000000000", value = "1000000000", groups = View.New.class)
    private BigDecimal amount;

}
