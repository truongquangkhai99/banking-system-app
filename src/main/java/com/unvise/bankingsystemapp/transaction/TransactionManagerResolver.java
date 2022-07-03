package com.unvise.bankingsystemapp.transaction;

import com.unvise.bankingsystemapp.account.account.Account;
import com.unvise.bankingsystemapp.credit.Credit;
import com.unvise.bankingsystemapp.currency.util.CurrencyConverter;
import com.unvise.bankingsystemapp.deposit.Deposit;
import com.unvise.bankingsystemapp.exception.TransactionFailedException;
import com.unvise.bankingsystemapp.transaction.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionManagerResolver {

    private final CurrencyConverter currencyConverter;

    public void manage(Transaction transaction) {
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
        }
    }

    private void manageCredit(Transaction transaction) throws TransactionFailedException {
        Account fromAccount = transaction.getTransactionDetails().getFromAccount();
        Credit neededCredit = findCreditByIdInList(
                fromAccount.getAccountHistory().getCredits(),
                transaction.getTransactionDetails().getCredit().getId()
        );

        checkBalanceByGreaterThanAmount(
                fromAccount.getBalance(),
                transaction.getTransactionDetails().getAmount()
        );

        checkCreditsByEmpty(fromAccount.getAccountHistory().getCredits());

        checkExistCredit(neededCredit, transaction.getTransactionDetails().getCredit().getId());

        // credit can't null because if it were, it would throw an TransactionFailedException in check methods
        BigDecimal transactionBalance = currencyConverter.convert(
                fromAccount.getCurrency(),
                Objects.requireNonNull(neededCredit).getCurrency(),
                transaction.getTransactionDetails().getAmount()
        );

        if (Objects.requireNonNull(neededCredit).getRemain().compareTo(transactionBalance) < 0) {
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

        fromAccount.subtractFromBalance(fromAccount.getBalance());
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

        fromAccount.subtractFromBalance(fromAccount.getBalance());
        toAccount.addToBalance(transactionBalance);
    }

    private void checkBalanceByGreaterThanAmount(BigDecimal balance, BigDecimal amount) throws TransactionFailedException {
        if (balance.compareTo(amount) < 0) {
            TransactionFailedException exception = new TransactionFailedException("Transaction failed because not" +
                    " enough money on from account balance"
            );

            exception.setFieldNameAndValue(Map.of(
                    "fromAccount balance", balance,
                    "Transaction amount", amount
            ));

            throw exception;
        }
    }

    private void checkCreditsByEmpty(List<Credit> credits) throws TransactionFailedException {
        if (credits.isEmpty()) {
            throw new TransactionFailedException("Transaction failed because from account don't have credits");
        }
    }

    private void checkExistCredit(Credit neededCredit, Long creditId) throws TransactionFailedException {
        if (neededCredit == null) {
            TransactionFailedException exception = new TransactionFailedException("Transaction failed because" +
                    " from account don't have credits with given id"
            );

            exception.setFieldNameAndValue(Map.of("Credit id", creditId));

            throw exception;
        }
    }

    private void checkExistDeposit(Deposit deposit) throws TransactionFailedException {
        if (deposit == null) {
            throw new TransactionFailedException("Transaction failed because" +
                    " from account don't have deposit"
            );
        }
    }

    private Credit findCreditByIdInList(List<Credit> credits, Long creditId) {
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
