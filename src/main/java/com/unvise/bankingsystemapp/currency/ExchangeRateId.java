package com.unvise.bankingsystemapp.currency;

import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExchangeRateId implements Serializable {

    private Long id;

    private CurrencyType fromCurrency;

    private CurrencyType toCurrency;

}
