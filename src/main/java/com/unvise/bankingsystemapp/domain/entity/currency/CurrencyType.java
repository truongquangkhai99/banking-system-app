package com.unvise.bankingsystemapp.domain.entity.currency;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CurrencyType {

    RUB("RUB"), USD("USD"), EUR("UER");

    @Getter
    private final String currencyTypeAsString;

}
