package com.unvise.bankingsystemapp.domain.transaction.details;

import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TransactionDetailsMapperHelper.class})
public interface TransactionDetailsMapper {

    @Mapping(source = "fromAccount", target = "fromAccountId", qualifiedByName = "getAccountId")
    @Mapping(source = "toAccount", target = "toAccountId", qualifiedByName = "getAccountId")
    @Mapping(source = "credit", target = "creditId", qualifiedByName = "getCreditId")
    TransactionDetailsDto toDto(TransactionDetails transactionDetails);

    @Mapping(target = "fromAccount", ignore = true)
    @Mapping(target = "toAccount", ignore = true)
    @Mapping(target = "credit", ignore = true)
    @Mapping(target = "currency", source = "currency")
    TransactionDetails toEntity(TransactionDetailsDto transactionDetailsDto);


}
