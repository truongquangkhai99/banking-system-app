package com.unvise.bankingsystemapp.domain.account.account;

import com.unvise.bankingsystemapp.domain.account.history.AccountHistoryMapper;
import com.unvise.bankingsystemapp.domain.account.security.AccountSecurityDetailsMapper;
import com.unvise.bankingsystemapp.domain.account.web.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AccountHistoryMapper.class, AccountSecurityDetailsMapper.class, AccountMapperHelper.class})
public interface AccountMapper {

    @Mapping(source = "accountHistory", target = "accountHistoryId", qualifiedByName = "accountHistoryId")
    AccountDto toDto(Account account);

    @Mapping(target = "accountHistory", ignore = true)
    Account toEntity(AccountDto accountDto);

    List<AccountDto> toDtoList(List<Account> accounts);

}
