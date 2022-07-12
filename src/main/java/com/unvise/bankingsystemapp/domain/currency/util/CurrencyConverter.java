package com.unvise.bankingsystemapp.domain.currency.util;

import com.unvise.bankingsystemapp.domain.currency.ExchangeRate;
import com.unvise.bankingsystemapp.domain.currency.ExchangeRateRepository;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.exception.resource.ResourceNotFoundException;
import com.unvise.bankingsystemapp.exception.resource.ResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CurrencyConverter {

    private final ExchangeRateRepository exchangeRateRepository;

    public BigDecimal convert(CurrencyType fromCurrency,
                              CurrencyType toCurrency,
                              BigDecimal value) throws ResourceNotFoundException {

        if (fromCurrency.equals(toCurrency)) {
            return value.multiply(BigDecimal.ONE);
        }

        ExchangeRate exchangeRate = exchangeRateRepository
                .findByFromCurrencyAndToCurrency(fromCurrency, toCurrency)
                .orElseThrow(() -> {
                    ResourceException e =
                            new ResourceNotFoundException("Can't find ExchangeRate with fromCurrency: " + fromCurrency +
                                    " and toCurrency: " + toCurrency);

                    e.setResourceName("ExchangeRate");
                    e.setFieldsAndValues(Map.of("fromCurrency", fromCurrency, "toCurrency", toCurrency));
                    return e;
                });

        return value.multiply(exchangeRate.getRatio());
    }

}
