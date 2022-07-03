package com.unvise.bankingsystemapp.account.history;

import com.unvise.bankingsystemapp.account.web.dto.AccountHistoryDto;
import com.unvise.bankingsystemapp.credit.CreditMapper;
import com.unvise.bankingsystemapp.deposit.DepositMapper;
import com.unvise.bankingsystemapp.transaction.transaction.TransactionMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DepositMapper.class, CreditMapper.class, TransactionMapper.class})
public interface AccountHistoryMapper {


    AccountHistoryDto toDto(AccountHistory accountHistory);

    AccountHistory toEntity(AccountHistoryDto accountHistoryDto);

    List<AccountHistoryDto> toDtoList(List<AccountHistory> accountHistories);

}
