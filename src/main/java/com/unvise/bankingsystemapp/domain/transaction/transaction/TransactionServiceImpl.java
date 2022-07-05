package com.unvise.bankingsystemapp.domain.transaction.transaction;

import com.unvise.bankingsystemapp.domain.account.account.Account;
import com.unvise.bankingsystemapp.domain.account.account.AccountRepository;
import com.unvise.bankingsystemapp.domain.credit.Credit;
import com.unvise.bankingsystemapp.domain.credit.CreditRepository;
import com.unvise.bankingsystemapp.exception.ResourceNotFoundException;
import com.unvise.bankingsystemapp.domain.transaction.TransactionManagerResolver;
import com.unvise.bankingsystemapp.domain.transaction.enums.TransactionType;
import com.unvise.bankingsystemapp.domain.transaction.web.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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


    @Override
    @Transactional
    public List<TransactionDto> getAll() {
        List<Transaction> foundTransactions = transactionRepository.findAll();
        return transactionMapper.toDtoList(foundTransactions);
    }

    @Override
    @Transactional
    public TransactionDto getById(Long aLong) {
        Transaction foundTransaction = transactionRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Transaction", Map.of("id", aLong)));
        return transactionMapper.toDto(foundTransaction);
    }

    @Override
    @Transactional
    public TransactionDto save(TransactionDto transactionDto) {
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
    public TransactionDto deleteById(Long aLong) {
        Transaction foundTransaction = transactionRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Transaction", Map.of("id", aLong)));
        transactionRepository.delete(foundTransaction);
        return transactionMapper.toDto(foundTransaction);
    }

    private Account getAccountByAccountHistoryId(Long id) {
        return accountRepository.findByAccountHistory_Id(id).orElseThrow(() ->
                new ResourceNotFoundException("AccountHistory", Map.of("id", id)));
    }

    private Credit getCreditById(Long id) {
        return creditRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Credit", Map.of("id", id)));
    }

}
