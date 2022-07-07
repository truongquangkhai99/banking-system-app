package com.unvise.bankingsystemapp.domain.account.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.domain.credit.web.dto.CreditDto;
import com.unvise.bankingsystemapp.domain.deposit.web.dto.DepositDto;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Null(message = "account_history must be null", groups = {View.New.class, View.Update.class})
public class AccountHistoryDto {

    private Long id;

    private DepositDto deposit;

    private List<CreditDto> credits;

    private List<TransactionDto> transaction;

}
