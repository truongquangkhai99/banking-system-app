package com.unvise.bankingsystemapp.account;

import com.unvise.bankingsystemapp.account.web.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(Account account);

    @Mapping(target = "account.balance", ignore = true)
    @Mapping(target = "account.accountHistory", ignore = true)
    Account toEntity(AccountDto accountDto);

    List<AccountDto> toDtoList(List<Account> accounts);
}
