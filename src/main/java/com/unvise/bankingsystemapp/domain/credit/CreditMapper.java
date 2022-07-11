package com.unvise.bankingsystemapp.domain.credit;

import com.unvise.bankingsystemapp.domain.credit.web.dto.CreditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    @Mapping(target = "accountHistoryId", expression = "java(credit.getAccountHistory().getId())")
    CreditDto toDto(Credit credit);

    @Mapping(target = "accountHistory", ignore = true)
    @Mapping(source = "currency", target = "currency")
    Credit toEntity(CreditDto creditDto);

    List<CreditDto> toDtoList(List<Credit> credits);

}
