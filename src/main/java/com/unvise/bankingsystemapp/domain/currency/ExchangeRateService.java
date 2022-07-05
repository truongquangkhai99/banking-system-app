package com.unvise.bankingsystemapp.domain.currency;

import com.unvise.bankingsystemapp.common.BaseService;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.domain.currency.web.dto.ExchangeRateDto;

import java.util.List;

public interface ExchangeRateService extends BaseService<ExchangeRateDto, Long> {

    List<ExchangeRateDto> getByToCurrency(CurrencyType toCurrency);

    List<ExchangeRateDto> getByFromCurrency(CurrencyType fromCurrency);

    ExchangeRateDto getByFromCurrencyAndToCurrency(CurrencyType fromCurrency, CurrencyType toCurrency);

}
