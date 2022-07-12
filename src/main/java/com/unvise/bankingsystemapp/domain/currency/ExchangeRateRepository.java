package com.unvise.bankingsystemapp.domain.currency;

import com.unvise.bankingsystemapp.domain.common.BaseRepository;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends BaseRepository<ExchangeRate, Long> {

    List<ExchangeRate> findByToCurrency(CurrencyType toCurrency);

    List<ExchangeRate> findByFromCurrency(CurrencyType fromCurrency);

    Optional<ExchangeRate> findByFromCurrencyAndToCurrency(CurrencyType fromCurrency, CurrencyType toCurrency);

}
