package com.unvise.bankingsystemapp.account.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    @Null(groups = {View.New.class, View.Update.class})
    private Long id;

    @Null(groups = {View.New.class})
    @NotNull(groups = {View.Update.class})
    private BigDecimal balance;

    @NotNull(groups = {View.New.class, View.Update.class})
    private CurrencyType currency;

    @NotNull(groups = {View.New.class, View.Update.class})
    private AccountSecurityDetailsDto accountSecurityDetails;

    @Null(groups = {View.New.class})
    @NotNull(groups = {View.Update.class})
    private Long accountHistoryId;

}
