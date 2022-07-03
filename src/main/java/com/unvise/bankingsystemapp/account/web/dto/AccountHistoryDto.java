package com.unvise.bankingsystemapp.account.web.dto;

import com.unvise.bankingsystemapp.common.View;
import com.unvise.bankingsystemapp.credit.web.dto.CreditDto;
import com.unvise.bankingsystemapp.deposit.web.dto.DepositDto;
import com.unvise.bankingsystemapp.transaction.web.dto.TransactionDto;
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
@Null(groups = {View.New.class, View.Update.class})
public class AccountHistoryDto {

    private Long id;

    private DepositDto deposit;

    private List<CreditDto> credits;

    private List<TransactionDto> transaction;

}
