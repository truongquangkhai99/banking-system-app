package com.unvise.bankingsystemapp.domain.account.account;

import com.unvise.bankingsystemapp.domain.account.security.AccountSecurityDetailsRepository;
import com.unvise.bankingsystemapp.domain.account.web.dto.AccountDto;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountSecurityDetailsRepository accountSecurityDetailsRepository;
    private final PasswordEncoder encoder;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public List<AccountDto> getAll() {
        List<Account> foundAccounts = accountRepository.findAll();
        return accountMapper.toDtoList(foundAccounts);
    }

    @Override
    @Transactional
    public AccountDto getById(Long aLong) {
        Account foundAccount = accountRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Account", Map.of("id", aLong)));
        return accountMapper.toDto(foundAccount);
    }

    @Override
    @Transactional
    public AccountDto save(AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);

        account.getAccountSecurityDetails().setPasswordHash(
                encoder.encode(accountDto.getAccountSecurityDetails().getPasswordHash())
        );

        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    @Transactional
    public AccountDto updateById(Long aLong, AccountDto accountDto) {
        accountRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Account", Map.of("id", aLong)));
        accountDto.setId(aLong);
        Account account = accountMapper.toEntity(accountDto);

        account.getAccountSecurityDetails().setId(
                // can't be null because the account always have accountSecurityDetails
                accountSecurityDetailsRepository.findAccountSecurityDetailsByAccount_Id(aLong).get().getId()
        );

        account.getAccountSecurityDetails().setPasswordHash(
                encoder.encode(accountDto.getAccountSecurityDetails().getPasswordHash())
        );

        account.setAccountHistory(accountRepository.findByAccountHistory_Id(accountDto.getAccountHistoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", Map.of("accountHistoryId", accountDto.getAccountHistoryId()))
        ).getAccountHistory());
        return accountMapper.toDto(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountDto deleteById(Long aLong) {
        Account foundAccount = accountRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Account", Map.of("id", aLong)));
        accountRepository.delete(foundAccount);
        return accountMapper.toDto(foundAccount);
    }

    @Override
    @Transactional
    public List<AccountDto> getByCurrency(CurrencyType currency) {
        List<Account> foundAccounts = accountRepository.findByCurrency(currency);
        return accountMapper.toDtoList(foundAccounts);
    }

}
