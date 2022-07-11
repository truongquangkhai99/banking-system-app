package com.unvise.bankingsystemapp.domain.currency;

import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.domain.currency.web.dto.ExchangeRateDto;
import com.unvise.bankingsystemapp.exception.resource.ResourceNotFoundException;
import com.unvise.bankingsystemapp.exception.resource.ResourceAlreadyExists;
import com.unvise.bankingsystemapp.exception.resource.ResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "exchange-rates")
    public List<ExchangeRateDto> getAll() {
        List<ExchangeRate> foundExchangeRates = exchangeRateRepository.findAll();
        return exchangeRateMapper.toDtoList(foundExchangeRates);
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "exchange-rates", key = "#aLong")
    public ExchangeRateDto getById(Long aLong) throws ResourceNotFoundException {
        ExchangeRate foundExchangeRate = exchangeRateRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find ExchangeRate with id: " + aLong);
            e.setResourceName("ExchangeRate");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;

        });

        return exchangeRateMapper.toDto(foundExchangeRate);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "exchange-rates", allEntries = true)
    public ExchangeRateDto save(ExchangeRateDto exchangeRateDto) throws ResourceAlreadyExists {
        if (exchangeRateRepository.findByFromCurrencyAndToCurrency(
                exchangeRateDto.getFromCurrency(),
                exchangeRateDto.getToCurrency()
        ).isPresent()) {
            ResourceException e =
                    new ResourceAlreadyExists("ExchangeRate with given currencies already exist.");

            e.setFieldsAndValues(Map.of(
                    "fromCurrency", exchangeRateDto.getFromCurrency(),
                    "toCurrency", exchangeRateDto.getToCurrency()
            ));

            throw e;
        }

        ExchangeRate savedExchangeRate = exchangeRateRepository.save(exchangeRateMapper.toEntity(exchangeRateDto));
        return exchangeRateMapper.toDto(savedExchangeRate);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "exchange-rates", allEntries = true)
    public ExchangeRateDto updateById(Long aLong, ExchangeRateDto exchangeRateDto)
            throws ResourceAlreadyExists, ResourceNotFoundException {

        if (exchangeRateRepository.findByFromCurrencyAndToCurrency(
                exchangeRateDto.getFromCurrency(), exchangeRateDto.getToCurrency()
        ).isPresent()) {
            ResourceException e =
                    new ResourceAlreadyExists("ExchangeRate with given currencies already exist.");

            e.setFieldsAndValues(
                    Map.of("fromCurrency", exchangeRateDto.getFromCurrency(),
                            "toCurrency", exchangeRateDto.getToCurrency())
            );

            throw e;
        }

        exchangeRateRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find ExchangeRate with id: " + aLong);
            e.setResourceName("ExchangeRate");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        exchangeRateDto.setId(aLong);
        ExchangeRate savedExchangeRate = exchangeRateRepository.save(exchangeRateMapper.toEntity(exchangeRateDto));
        return exchangeRateMapper.toDto(savedExchangeRate);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "exchange-rates", key = "#aLong")
    public ExchangeRateDto deleteById(Long aLong) throws ResourceNotFoundException {
        ExchangeRate foundExchangeRate = exchangeRateRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find ExchangeRate with id: " + aLong);
            e.setResourceName("ExchangeRate");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        exchangeRateRepository.delete(foundExchangeRate);
        return exchangeRateMapper.toDto(foundExchangeRate);
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "exchange-rates", key = "#toCurrency")
    public List<ExchangeRateDto> getByToCurrency(CurrencyType toCurrency) {
        List<ExchangeRate> foundExchangeRates = exchangeRateRepository.findByToCurrency(toCurrency);
        return exchangeRateMapper.toDtoList(foundExchangeRates);
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "exchange-rates", key = "#fromCurrency")
    public List<ExchangeRateDto> getByFromCurrency(CurrencyType fromCurrency) {
        List<ExchangeRate> foundExchangeRates = exchangeRateRepository.findByFromCurrency(fromCurrency);
        return exchangeRateMapper.toDtoList(foundExchangeRates);
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "exchange-rates", key = "#fromCurrency.currencyTypeAsString.concat('-').concat(#toCurrency.currencyTypeAsString)")
    public ExchangeRateDto getByFromCurrencyAndToCurrency(CurrencyType fromCurrency, CurrencyType toCurrency)
            throws ResourceNotFoundException {

        ExchangeRate foundExchangeRate =
                exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency).orElseThrow(() -> {
                    ResourceException e =
                            new ResourceNotFoundException("Can't find ExchangeRate with fromCurrency: " + fromCurrency +
                                    " and ToCurrency: " + toCurrency);

                    e.setResourceName("ExchangeRate");
                    e.setFieldsAndValues(Map.of(
                            "fromCurrency", fromCurrency,
                            "toCurrency", toCurrency
                    ));

                    return e;
                });

        return exchangeRateMapper.toDto(foundExchangeRate);
    }


}
