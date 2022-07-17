package com.unvise.bankingsystemapp.domain.transaction.transaction;

import com.unvise.bankingsystemapp.domain.account.account.Account;
import com.unvise.bankingsystemapp.domain.account.account.AccountRepository;
import com.unvise.bankingsystemapp.domain.credit.Credit;
import com.unvise.bankingsystemapp.domain.credit.CreditRepository;
import com.unvise.bankingsystemapp.domain.transaction.TransactionManagerResolver;
import com.unvise.bankingsystemapp.domain.transaction.enums.TransactionType;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDto;
import com.unvise.bankingsystemapp.exception.resource.ResourceNotFoundException;
import com.unvise.bankingsystemapp.exception.transaction.TransactionFailedException;
import com.unvise.bankingsystemapp.exception.resource.ResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CreditRepository creditRepository;
    private final TransactionManagerResolver transactionManagerResolver;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    public static List<TransactionDto> sortTransactionsInReverseOrder(List<TransactionDto> transactions) {
        List<TransactionDto> transactionCopy = new ArrayList<>(transactions);
        transactionCopy.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));

        return transactionCopy;
    }

    @Override
    @Transactional
    @Cacheable("transactions")
    public List<TransactionDto> getAll() {
        List<Transaction> foundTransactions = transactionRepository.findAll();
        return transactionMapper.toDtoList(foundTransactions);
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "transactions", key = "#aLong")
    public TransactionDto getById(Long aLong) throws ResourceNotFoundException {
        Transaction foundTransaction = transactionRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find Transaction with id: " + aLong);

            e.setResourceName("Transaction");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        return transactionMapper.toDto(foundTransaction);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "transactions", allEntries = true)
    public TransactionDto save(TransactionDto transactionDto) throws TransactionFailedException {
        Account fromAccount = getAccountByAccountHistoryId(transactionDto.getTransactionDetails().getFromAccountId());

        Transaction transaction = transactionMapper.toEntity(transactionDto);
        transaction.getTransactionDetails().setTransaction(transaction);
        transaction.getTransactionDetails().setFromAccount(fromAccount);

        transaction.setAccountHistory(fromAccount.getAccountHistory());

        if (transactionDto.getTransactionDetails().getTransactionType().equals(TransactionType.CREDIT)) {
            transaction
                    .getTransactionDetails()
                    .setCredit(getCreditById(transactionDto.getTransactionDetails().getCreditId()));
        }

        if (transactionDto.getTransactionDetails().getTransactionType().equals(TransactionType.TRANSFER)) {
            Account toAccount = getAccountByAccountHistoryId(transactionDto.getTransactionDetails().getToAccountId());
            transaction.getTransactionDetails().setToAccount(toAccount);
        }

        transactionManagerResolver.manage(transaction);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toDto(savedTransaction);
    }

    @Override
    @Transactional
    public TransactionDto updateById(Long aLong, TransactionDto transactionDto) {
        // transaction can't be change
        return null;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "transactions", allEntries = true)
    public TransactionDto deleteById(Long aLong) throws ResourceNotFoundException {
        Transaction foundTransaction = transactionRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find Transaction with id: " + aLong);

            e.setResourceName("Transaction");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        transactionRepository.delete(foundTransaction);
        return transactionMapper.toDto(foundTransaction);
    }

    private Account getAccountByAccountHistoryId(Long id) throws ResourceNotFoundException {
        return accountRepository.findByAccountHistory_Id(id).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find AccountHistory with id: " + id);

            e.setResourceName("Transaction -> AccountHistory");
            e.setFieldsAndValues(Map.of("id", id));

            return e;
        });
    }

    private Credit getCreditById(Long id) throws ResourceNotFoundException {
        return creditRepository.findById(id).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find Credit with id: " + id);

            e.setResourceName("Transaction -> Credit");
            e.setFieldsAndValues(Map.of("id", id));

            return e;
        });
    }

}
