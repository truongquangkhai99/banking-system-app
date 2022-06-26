package com.unvise.bankingsystemapp.account;

import com.unvise.bankingsystemapp.account.web.dto.AccountDto;
import com.unvise.bankingsystemapp.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<AccountDto> getAll() {
        List<Account> foundAccounts = accountRepository.findAll();
        return accountMapper.toDtoList(foundAccounts);
    }

    @Override
    public AccountDto getById(Long aLong) {
        Account foundAccount = accountRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Account", Map.of("id", aLong)));
        return accountMapper.toDto(foundAccount);
    }

    @Override
    public AccountDto save(AccountDto accountDto) {
        Account savedAccount = accountRepository.save(accountMapper.toEntity(accountDto));
        return accountMapper.toDto(savedAccount);
    }

    @Override
    public AccountDto updateById(Long aLong, AccountDto accountDto) {
        accountRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Account", Map.of("id", aLong)));
        accountDto.setId(aLong);
        Account account = accountRepository.save(accountMapper.toEntity(accountDto));
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto deleteById(Long aLong) {
        Account foundAccount = accountRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Account", Map.of("id", aLong)));
        accountRepository.delete(foundAccount);
        return accountMapper.toDto(foundAccount);
    }

    @Override
    public List<AccountDto> getByCurrency(CurrencyType currency) {
        List<Account> foundAccounts = accountRepository.findByCurrency(currency);
        return accountMapper.toDtoList(foundAccounts);
    }

}
