package com.unvise.bankingsystemapp.domain.transaction.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransactionType {

    DEPOSIT("DEPOSIT"), TRANSFER("TRANSFER"), CREDIT("CREDIT");

    @Getter
    private final String transactionTypeAsString;

}
