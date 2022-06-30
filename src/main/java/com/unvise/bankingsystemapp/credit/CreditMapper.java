package com.unvise.bankingsystemapp.credit;

import com.unvise.bankingsystemapp.credit.web.dto.CreditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    @Mapping(target = "accountHistoryId", ignore = true)
    CreditDto toDto(Credit credit);

    @Mapping(target = "accountHistory", ignore = true)
    Credit toEntity(CreditDto creditDto);

    List<CreditDto> toDtoList(List<Credit> credits);

}
