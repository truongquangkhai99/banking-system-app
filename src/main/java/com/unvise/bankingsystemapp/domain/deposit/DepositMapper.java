package com.unvise.bankingsystemapp.domain.deposit;

import com.unvise.bankingsystemapp.domain.account.account.AccountMapperHelper;
import com.unvise.bankingsystemapp.domain.deposit.web.dto.DepositDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AccountMapperHelper.class})
public interface DepositMapper {

    @Mapping(source = "accountHistory", target = "accountHistoryId", qualifiedByName = "accountHistoryId")
    @Mapping(source = "currency", target = "currency")
    DepositDto toDto(Deposit deposit);

    @Mapping(target = "accountHistory", ignore = true)
    @Mapping(source = "currency", target = "currency")
    Deposit toEntity(DepositDto depositDto);

    List<DepositDto> toDtoList(List<Deposit> deposits);

}
