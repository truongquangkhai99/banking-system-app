package com.unvise.bankingsystemapp.domain.deposit;

import com.unvise.bankingsystemapp.domain.account.account.Account;
import com.unvise.bankingsystemapp.domain.account.account.AccountRepository;
import com.unvise.bankingsystemapp.domain.deposit.web.dto.DepositDto;
import com.unvise.bankingsystemapp.exception.resource.ResourceNotFoundException;
import com.unvise.bankingsystemapp.exception.resource.ResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final DepositRepository depositRepository;
    private final AccountRepository accountRepository;
    private final DepositMapper depositMapper;

    @Override
    @Transactional
    @Cacheable("deposits")
    public List<DepositDto> getAll() {
        List<Deposit> foundDeposits = depositRepository.findAll();
        return depositMapper.toDtoList(foundDeposits);
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "deposits", key = "#aLong")
    public DepositDto getById(Long aLong) throws ResourceNotFoundException {
        Deposit foundDeposit = depositRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find deposit with id: " + aLong);

            e.setResourceName("Deposit");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        return depositMapper.toDto(foundDeposit);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "deposits", allEntries = true)
    public DepositDto save(DepositDto depositDto) {
        Deposit deposit = depositMapper.toEntity(depositDto);

        Account account = getAccountByAccountHistoryId(depositDto.getAccountHistoryId());
        account.getAccountHistory().setDeposit(deposit);

        Deposit savedDeposit = depositRepository.save(deposit);

        return depositMapper.toDto(savedDeposit);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "deposits", allEntries = true)
    public DepositDto updateById(Long aLong, DepositDto depositDto) throws ResourceNotFoundException {
        depositRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find deposit with id: " + aLong);

            e.setResourceName("Deposit");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        depositDto.setId(aLong);
        Deposit updatedDeposit = depositRepository.save(depositMapper.toEntity(depositDto));
        return depositMapper.toDto(updatedDeposit);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "deposits", allEntries = true)
    public DepositDto deleteById(Long aLong) throws ResourceNotFoundException {
        Deposit foundDeposit = depositRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find deposit with id: " + aLong);

            e.setResourceName("Deposit");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        Account account = getAccountByAccountHistoryId(foundDeposit.getAccountHistory().getId());
        account.getAccountHistory().setDeposit(null);

        depositRepository.delete(foundDeposit);
        return depositMapper.toDto(foundDeposit);
    }

    private Account getAccountByAccountHistoryId(Long id) throws ResourceNotFoundException {
        return accountRepository.findByAccountHistory_Id(id).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find deposit with id: " + id);

            e.setResourceName("Deposit -> AccountHistory");
            e.setFieldsAndValues(Map.of("id", id));

            return e;
        });
    }

}
