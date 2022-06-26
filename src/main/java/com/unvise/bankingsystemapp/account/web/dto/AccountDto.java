package com.unvise.bankingsystemapp.account.web.dto;

import com.fasterxml.jackson.annotation.JsonView;
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

    @Null(groups = {AccountViews.New.class})
    @JsonView({AccountViews.Details.class})
    private Long id;

    @Null(groups = {AccountViews.New.class})
    @JsonView({AccountViews.Details.class})
    private BigDecimal balance;

    @NotNull(groups = {AccountViews.New.class})
    @JsonView({AccountViews.Details.class})
    private CurrencyType currency;

    @NotNull(groups = {AccountViews.New.class})
    private AccountSecurityDetailsDto accountSecurityDetails;

    @Null(groups = {AccountViews.New.class})
    private AccountHistoryDto accountHistory;

}
