package com.unvise.bankingsystemapp.deposit;

import com.unvise.bankingsystemapp.deposit.web.dto.DepositDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepositMapper {

    @Mapping(target = "accountHistoryId", ignore = true)
    @Mapping(source = "currency", target = "currency")
    DepositDto toDto(Deposit deposit);

    @Mapping(target = "accountHistory", ignore = true)
    @Mapping(source = "currency", target = "currency")
    Deposit toEntity(DepositDto depositDto);

    List<DepositDto> toDtoList(List<Deposit> deposits);

}
