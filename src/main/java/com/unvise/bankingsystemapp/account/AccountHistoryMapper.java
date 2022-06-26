package com.unvise.bankingsystemapp.account;

import com.unvise.bankingsystemapp.account.web.dto.AccountHistoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountHistoryMapper {

    AccountHistoryDto toDto(AccountHistory accountHistory);

    AccountHistory toEntity(AccountHistoryDto accountHistoryDto);

    List<AccountHistoryDto> toDtoList(List<AccountHistory> accountHistories);

}
