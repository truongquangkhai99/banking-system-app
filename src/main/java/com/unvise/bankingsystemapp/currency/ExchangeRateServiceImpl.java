package com.unvise.bankingsystemapp.currency;

import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.currency.web.dto.ExchangeRateDto;
import com.unvise.bankingsystemapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateMapper exchangeRateMapper;

    @Override
    public List<ExchangeRateDto> getAll() {
        List<ExchangeRate> foundExchangeRates = exchangeRateRepository.findAll();
        return exchangeRateMapper.toDtoList(foundExchangeRates);
    }

    @Override
    public ExchangeRateDto getById(Long aLong) {
        ExchangeRate foundExchangeRate = exchangeRateRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("ExchangeRate", Map.of("id", aLong)));
        return exchangeRateMapper.toDto(foundExchangeRate);
    }

    @Override
    public ExchangeRateDto save(ExchangeRateDto exchangeRateDto) {
        ExchangeRate savedExchangeRate = exchangeRateRepository.save(exchangeRateMapper.toEntity(exchangeRateDto));
        return exchangeRateMapper.toDto(savedExchangeRate);
    }

    @Override
    public ExchangeRateDto updateById(Long aLong, ExchangeRateDto exchangeRateDto) {
        exchangeRateRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("ExchangeRate", Map.of("id", aLong)));
        exchangeRateDto.setId(aLong);
        ExchangeRate savedExchangeRate = exchangeRateRepository.save(exchangeRateMapper.toEntity(exchangeRateDto));
        return exchangeRateMapper.toDto(savedExchangeRate);
    }

    @Override
    public ExchangeRateDto deleteById(Long aLong) {
        ExchangeRate foundExchangeRate = exchangeRateRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("ExchangeRate", Map.of("id", aLong)));
        exchangeRateRepository.delete(foundExchangeRate);
        return exchangeRateMapper.toDto(foundExchangeRate);
    }

    @Override
    public List<ExchangeRateDto> getByToCurrency(CurrencyType toCurrency) {
        List<ExchangeRate> foundExchangeRates = exchangeRateRepository.findByToCurrency(toCurrency);
        return exchangeRateMapper.toDtoList(foundExchangeRates);
    }

    @Override
    public List<ExchangeRateDto> getByFromCurrency(CurrencyType fromCurrency) {
        List<ExchangeRate> foundExchangeRates = exchangeRateRepository.findByFromCurrency(fromCurrency);
        return exchangeRateMapper.toDtoList(foundExchangeRates);
    }

    @Override
    public ExchangeRateDto getByFromCurrencyAndToCurrency(CurrencyType fromCurrency, CurrencyType toCurrency) {
        ExchangeRate foundExchangeRate =
                exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency).orElseThrow(() ->
                        new ResourceNotFoundException("ExchangeRate", Map.of(
                                "fromCurrency", fromCurrency,
                                "toCurrecny", toCurrency
                        ))
                );
        return exchangeRateMapper.toDto(foundExchangeRate);
    }


}
