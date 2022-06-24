package com.unvise.bankingsystemapp.currency;

import com.unvise.bankingsystemapp.common.BaseService;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.currency.web.dto.ExchangeRateDto;

import java.util.List;

public interface ExchangeRateService extends BaseService<ExchangeRateDto, Long> {

    List<ExchangeRateDto> getByToCurrency(CurrencyType toCurrency);

    List<ExchangeRateDto> getByFromCurrency(CurrencyType fromCurrency);

    ExchangeRateDto getByFromCurrencyAndToCurrency(CurrencyType fromCurrency, CurrencyType toCurrency);

}
