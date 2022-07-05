package com.unvise.bankingsystemapp.domain.transaction.transaction;

import com.unvise.bankingsystemapp.domain.transaction.details.TransactionDetailsMapper;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TransactionDetailsMapper.class})
public interface TransactionMapper {

    TransactionDto toDto(Transaction transaction);

    @Mapping(target = "date", dateFormat = "yyyy-MM-dd")
    Transaction toEntity(TransactionDto transactionDto);

    List<TransactionDto> toDtoList(List<Transaction> transactionList);

}
