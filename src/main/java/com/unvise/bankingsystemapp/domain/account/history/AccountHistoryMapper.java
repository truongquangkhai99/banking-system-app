package com.unvise.bankingsystemapp.domain.account.history;

import com.unvise.bankingsystemapp.domain.account.web.dto.AccountHistoryDto;
import com.unvise.bankingsystemapp.domain.credit.CreditMapper;
import com.unvise.bankingsystemapp.domain.deposit.DepositMapper;
import com.unvise.bankingsystemapp.domain.transaction.transaction.TransactionMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DepositMapper.class, CreditMapper.class, TransactionMapper.class})
public interface AccountHistoryMapper {


    AccountHistoryDto toDto(AccountHistory accountHistory);

    AccountHistory toEntity(AccountHistoryDto accountHistoryDto);

    List<AccountHistoryDto> toDtoList(List<AccountHistory> accountHistories);

}
