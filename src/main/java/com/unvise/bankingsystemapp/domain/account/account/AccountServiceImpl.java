package com.unvise.bankingsystemapp.domain.account.account;

import com.unvise.bankingsystemapp.domain.account.security.AccountSecurityDetailsRepository;
import com.unvise.bankingsystemapp.domain.account.web.dto.AccountDto;
import com.unvise.bankingsystemapp.domain.currency.enums.CurrencyType;
import com.unvise.bankingsystemapp.exception.resource.ResourceNotFoundException;
import com.unvise.bankingsystemapp.exception.resource.ResourceException;
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
    public AccountDto getById(Long aLong) throws ResourceNotFoundException {
        Account foundAccount = accountRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find Account with id: " + aLong);
            e.setResourceName("Account");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        return accountMapper.toDto(foundAccount);
    }

    /**
     * Account saving with person. Account can't be safe directly.
     */
    @Override
    @Transactional
    public AccountDto save(AccountDto accountDto) {
        return null;
    }

    @Override
    @Transactional
    public AccountDto updateById(Long aLong, AccountDto accountDto) throws ResourceNotFoundException {
        accountRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find Account with id: " + aLong);
            e.setResourceName("Account");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        accountDto.setId(aLong);
        Account account = accountMapper.toEntity(accountDto);

        account.getAccountSecurityDetails().setId(
                // can't be null because the account always have accountSecurityDetails
                accountSecurityDetailsRepository.findAccountSecurityDetailsByAccount_Id(aLong).get().getId()
        );

        account.getAccountSecurityDetails().setPasswordHash(
                encoder.encode(accountDto.getAccountSecurityDetails().getPasswordHash())
        );

        account.setAccountHistory(accountRepository.findByAccountHistory_Id(accountDto.getAccountHistoryId()).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find AccountHistory id: " + aLong);
            e.setResourceName("Account -> AccountHistory");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        }).getAccountHistory());

        return accountMapper.toDto(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountDto deleteById(Long aLong) {
        Account foundAccount = accountRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find Account with id: " + aLong);
            e.setResourceName("Account");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

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
