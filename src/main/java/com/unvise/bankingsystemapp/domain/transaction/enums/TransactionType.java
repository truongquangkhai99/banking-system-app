package com.unvise.bankingsystemapp.domain.transaction.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransactionType {

    DEPOSIT("DEPOSIT"),
    TRANSFER("TRANSFER"),
    CREDIT("CREDIT"),
    TOP_UP_ACCOUNT_BALANCE("TOP_UP_ACCOUNT_BALANCE"),
    WITHDRAW_DEPOSIT("WITHDRAW_DEPOSIT");

    @Getter
    private final String transactionTypeAsString;

}
