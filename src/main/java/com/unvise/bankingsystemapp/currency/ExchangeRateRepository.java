package com.unvise.bankingsystemapp.currency;

import com.unvise.bankingsystemapp.common.BaseRepository;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends BaseRepository<ExchangeRate, ExchangeRateId> {

    Optional<ExchangeRate> findById(Long id);

    List<ExchangeRate> findByToCurrency(CurrencyType toCurrency);

    List<ExchangeRate> findByFromCurrency(CurrencyType fromCurrency);

    Optional<ExchangeRate> findByFromCurrencyAndToCurrency(CurrencyType fromCurrency, CurrencyType toCurrency);

}
