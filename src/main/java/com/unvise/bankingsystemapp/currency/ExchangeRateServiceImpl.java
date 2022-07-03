package com.unvise.bankingsystemapp.currency;

import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.currency.web.dto.ExchangeRateDto;
import com.unvise.bankingsystemapp.exception.ExchangeRateAlreadyExistException;
import com.unvise.bankingsystemapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateMapper exchangeRateMapper;

    @Override
    @Transactional
    public List<ExchangeRateDto> getAll() {
        List<ExchangeRate> foundExchangeRates = exchangeRateRepository.findAll();
        return exchangeRateMapper.toDtoList(foundExchangeRates);
    }

    @Override
    @Transactional
    public ExchangeRateDto getById(Long aLong) {
        ExchangeRate foundExchangeRate = exchangeRateRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("ExchangeRate", Map.of("id", aLong)));
        return exchangeRateMapper.toDto(foundExchangeRate);
    }

    @Override
    @Transactional
    public ExchangeRateDto save(ExchangeRateDto exchangeRateDto) {
        if (exchangeRateRepository.findByFromCurrencyAndToCurrency(
                exchangeRateDto.getFromCurrency(), exchangeRateDto.getToCurrency()
        ).isPresent()) {
            ExchangeRateAlreadyExistException exception =
                    new ExchangeRateAlreadyExistException("Exchange rate with given currencies already exist.");

            exception.setFieldNameAndValue(
                    Map.of("fromCurrency", exchangeRateDto.getFromCurrency(),
                            "toCurrency", exchangeRateDto.getToCurrency())
            );

            throw exception;
        }
        ExchangeRate savedExchangeRate = exchangeRateRepository.save(exchangeRateMapper.toEntity(exchangeRateDto));
        return exchangeRateMapper.toDto(savedExchangeRate);
    }

    @Override
    @Transactional
    public ExchangeRateDto updateById(Long aLong, ExchangeRateDto exchangeRateDto) {
        if (exchangeRateRepository.findByFromCurrencyAndToCurrency(
                exchangeRateDto.getFromCurrency(), exchangeRateDto.getToCurrency()
        ).isPresent()) {
            ExchangeRateAlreadyExistException exception =
                    new ExchangeRateAlreadyExistException("Exchange rate with given currencies already exist.");

            exception.setFieldNameAndValue(
                    Map.of("fromCurrency", exchangeRateDto.getFromCurrency(),
                            "toCurrency", exchangeRateDto.getToCurrency())
            );

            throw exception;
        }

        exchangeRateRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("ExchangeRate", Map.of("id", aLong)));
        exchangeRateDto.setId(aLong);
        ExchangeRate savedExchangeRate = exchangeRateRepository.save(exchangeRateMapper.toEntity(exchangeRateDto));
        return exchangeRateMapper.toDto(savedExchangeRate);
    }

    @Override
    @Transactional
    public ExchangeRateDto deleteById(Long aLong) {
        ExchangeRate foundExchangeRate = exchangeRateRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("ExchangeRate", Map.of("id", aLong)));
        exchangeRateRepository.delete(foundExchangeRate);
        return exchangeRateMapper.toDto(foundExchangeRate);
    }

    @Override
    @Transactional
    public List<ExchangeRateDto> getByToCurrency(CurrencyType toCurrency) {
        List<ExchangeRate> foundExchangeRates = exchangeRateRepository.findByToCurrency(toCurrency);
        return exchangeRateMapper.toDtoList(foundExchangeRates);
    }

    @Override
    @Transactional
    public List<ExchangeRateDto> getByFromCurrency(CurrencyType fromCurrency) {
        List<ExchangeRate> foundExchangeRates = exchangeRateRepository.findByFromCurrency(fromCurrency);
        return exchangeRateMapper.toDtoList(foundExchangeRates);
    }

    @Override
    @Transactional
    public ExchangeRateDto getByFromCurrencyAndToCurrency(CurrencyType fromCurrency, CurrencyType toCurrency) {
        ExchangeRate foundExchangeRate =
                exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency).orElseThrow(() ->
                        new ResourceNotFoundException("ExchangeRate", Map.of(
                                "fromCurrency", fromCurrency,
                                "toCurrency", toCurrency
                        ))
                );
        return exchangeRateMapper.toDto(foundExchangeRate);
    }


}
