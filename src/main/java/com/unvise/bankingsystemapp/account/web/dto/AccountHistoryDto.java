package com.unvise.bankingsystemapp.account.web.dto;

import com.unvise.bankingsystemapp.credit.Credit;
import com.unvise.bankingsystemapp.deposit.Deposit;
import com.unvise.bankingsystemapp.transaction.Transaction;
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
public class AccountHistoryDto {

    @Null
    private Long id;

    private Deposit deposit;

    private List<Credit> credits;

    private List<Transaction> transaction;

}
