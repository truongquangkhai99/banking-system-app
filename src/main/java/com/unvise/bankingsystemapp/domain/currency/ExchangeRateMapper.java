package com.unvise.bankingsystemapp.domain.currency;

import com.unvise.bankingsystemapp.domain.currency.web.dto.ExchangeRateDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {

    ExchangeRateDto toDto(ExchangeRate exchangeRate);

    ExchangeRate toEntity(ExchangeRateDto exchangeRateDto);

    List<ExchangeRateDto> toDtoList(List<ExchangeRate> exchangeRates);

}
