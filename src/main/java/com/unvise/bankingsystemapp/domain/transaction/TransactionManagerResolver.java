package com.unvise.bankingsystemapp.domain.transaction;

import com.unvise.bankingsystemapp.domain.account.account.Account;
import com.unvise.bankingsystemapp.domain.credit.Credit;
import com.unvise.bankingsystemapp.domain.currency.util.CurrencyConverter;
import com.unvise.bankingsystemapp.domain.deposit.Deposit;
import com.unvise.bankingsystemapp.exception.transaction.TransactionFailedException;
import com.unvise.bankingsystemapp.domain.transaction.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionManagerResolver implements TransactionManager {

    private final CurrencyConverter currencyConverter;

    @Override
    public void manage(Transaction transaction) throws TransactionFailedException {
        switch (transaction.getTransactionDetails().getTransactionType()) {
            case CREDIT:
                manageCredit(transaction);
                break;

            case DEPOSIT:
                manageDeposit(transaction);
                break;

            case TRANSFER:
                manageTransfer(transaction);
                break;

            case TOP_UP_ACCOUNT_BALANCE:
                manageTopUpAccountBalance(transaction);
                break;

            case WITHDRAW_DEPOSIT:
                manageWithdrawDeposit(transaction);
                break;
        }
    }

    private void manageCredit(Transaction transaction) throws TransactionFailedException {
        Account fromAccount = transaction.getTransactionDetails().getFromAccount();
        Credit neededCredit = findCreditByIdInCollection(
                fromAccount.getAccountHistory().getCredits(),
                transaction.getTransactionDetails().getCredit().getId()
        );

        checkBalanceByGreaterThanAmount(
                fromAccount.getBalance(),
                transaction.getTransactionDetails().getAmount()
        );

        checkCreditsByEmpty(fromAccount.getAccountHistory().getCredits());

        checkExistCredit(neededCredit, transaction.getTransactionDetails().getCredit().getId());

        BigDecimal transactionBalance = currencyConverter.convert(
                fromAccount.getCurrency(),
                Objects.requireNonNull(neededCredit).getCurrency(),
                transaction.getTransactionDetails().getAmount()
        );

        if (neededCredit.getRemain().compareTo(transactionBalance) < 0) {
            transaction.getTransactionDetails().setAmount(neededCredit.getRemain());

            BigDecimal creditBalanceInFromAccountCurrency = currencyConverter.convert(
                    Objects.requireNonNull(neededCredit).getCurrency(),
                    fromAccount.getCurrency(),
                    neededCredit.getRemain()
            );

            fromAccount.subtractFromBalance(creditBalanceInFromAccountCurrency);

            neededCredit.topUp(neededCredit.getRemain());

            neededCredit.setIsClosed(true);
            return;
        }

        fromAccount.subtractFromBalance(transaction.getTransactionDetails().getAmount());

        neededCredit.topUp(transactionBalance);
    }

    private void manageDeposit(Transaction transaction) throws TransactionFailedException {
        Account fromAccount = transaction.getTransactionDetails().getFromAccount();

        checkExistDeposit(fromAccount.getAccountHistory().getDeposit());

        checkBalanceByGreaterThanAmount(
                fromAccount.getBalance(),
                transaction.getTransactionDetails().getAmount()
        );

        BigDecimal transactionBalance = currencyConverter.convert(
                fromAccount.getCurrency(),
                fromAccount.getAccountHistory().getDeposit().getCurrency(),
                transaction.getTransactionDetails().getAmount()
        );

        fromAccount.subtractFromBalance(transaction.getTransactionDetails().getAmount());
        fromAccount.getAccountHistory().getDeposit().addToBalance(transactionBalance);
    }

    private void manageTransfer(Transaction transaction) throws TransactionFailedException {
        Account fromAccount = transaction.getTransactionDetails().getFromAccount();
        Account toAccount = transaction.getTransactionDetails().getToAccount();

        checkBalanceByGreaterThanAmount(
                fromAccount.getBalance(),
                transaction.getTransactionDetails().getAmount()
        );

        BigDecimal transactionBalance = currencyConverter.convert(
                fromAccount.getCurrency(),
                toAccount.getCurrency(),
                transaction.getTransactionDetails().getAmount()
        );

        fromAccount.subtractFromBalance(transaction.getTransactionDetails().getAmount());
        toAccount.addToBalance(transactionBalance);
    }

    private void manageTopUpAccountBalance(Transaction transaction) throws TransactionFailedException {
        Account fromAccount = transaction.getTransactionDetails().getFromAccount();

        BigDecimal convertedTransactionBalance = currencyConverter.convert(
                transaction.getTransactionDetails().getCurrency(),
                fromAccount.getCurrency(),
                transaction.getTransactionDetails().getAmount()
        );

        fromAccount.addToBalance(convertedTransactionBalance);
    }

    private void manageWithdrawDeposit(Transaction transaction) throws TransactionFailedException {
        Account fromAccount = transaction.getTransactionDetails().getFromAccount();

        Deposit deposit = fromAccount.getAccountHistory().getDeposit();

        BigDecimal convertedTransactionBalance = currencyConverter.convert(
                fromAccount.getCurrency(),
                deposit.getCurrency(),
                transaction.getTransactionDetails().getAmount()
        );

        fromAccount.addToBalance(convertedTransactionBalance);
        deposit.subtractFromBalance(transaction.getTransactionDetails().getAmount());
    }

    private void checkBalanceByGreaterThanAmount(BigDecimal balance, BigDecimal amount) throws TransactionFailedException {
        if (balance.compareTo(amount) < 0) {
            TransactionFailedException e = new TransactionFailedException("Transaction failed because not" +
                    " enough money on from account balance"
            );

            e.setFieldsAndValues(Map.of(
                    "fromAccount balance", balance,
                    "Transaction amount", amount
            ));

            throw e;
        }
    }

    private void checkCreditsByEmpty(Collection<Credit> credits) throws TransactionFailedException {
        if (credits.isEmpty()) {
            throw new TransactionFailedException("Transaction failed because from account don't have credits");
        }
    }

    private void checkExistCredit(Credit neededCredit, Long creditId) throws TransactionFailedException {
        if (neededCredit == null) {
            TransactionFailedException e = new TransactionFailedException("Transaction failed because" +
                    " from account don't have credits with given id"
            );

            e.setFieldsAndValues(Map.of("Credit id", creditId));

            throw e;
        }
    }

    private void checkExistDeposit(Deposit deposit) throws TransactionFailedException {
        if (deposit == null) {
            throw new TransactionFailedException("Transaction failed because" +
                    " from account don't have deposit"
            );
        }
    }

    private Credit findCreditByIdInCollection(Collection<Credit> credits, Long creditId) {
        if (credits.isEmpty()) {
            return null;
        }

        List<Credit> filteredCredits = credits.stream()
                .filter(credit -> credit.getId().equals(creditId))
                .collect(Collectors.toList());

        if (filteredCredits.isEmpty()) {
            return null;
        }

        return filteredCredits.get(0);
    }

}
