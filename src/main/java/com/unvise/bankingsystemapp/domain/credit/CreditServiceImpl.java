package com.unvise.bankingsystemapp.domain.credit;

import com.unvise.bankingsystemapp.domain.account.account.Account;
import com.unvise.bankingsystemapp.domain.account.account.AccountRepository;
import com.unvise.bankingsystemapp.domain.credit.web.dto.CreditDto;
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
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final AccountRepository accountRepository;
    private final CreditMapper creditMapper;

    @Override
    @Transactional
    @Cacheable("credits")
    public List<CreditDto> getAll() {
        List<Credit> foundCredits = creditRepository.findAll();
        return creditMapper.toDtoList(foundCredits);
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "credits", key = "#aLong")
    public CreditDto getById(Long aLong) throws ResourceNotFoundException {
        Credit foundCredit = creditRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find Credit with id: " + aLong);
            e.setResourceName("Credit");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        return creditMapper.toDto(foundCredit);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "credits", allEntries = true)
    public CreditDto save(CreditDto creditDto) {
        Account foundAccount = getAccountByAccountHistoryId(creditDto.getAccountHistoryId());

        Credit credit = creditMapper.toEntity(creditDto);
        credit.setAccountHistory(foundAccount.getAccountHistory());

        Credit savedCredit = creditRepository.save(credit);
        return creditMapper.toDto(savedCredit);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "credits", allEntries = true)
    public CreditDto updateById(Long aLong, CreditDto creditDto) throws ResourceNotFoundException {
        creditRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find Credit with id: " + aLong);
            e.setResourceName("Credit");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;

        });

        creditDto.setId(aLong);

        Account foundAccount = getAccountByAccountHistoryId(creditDto.getAccountHistoryId());

        Credit credit = creditMapper.toEntity(creditDto);
        credit.setAccountHistory(foundAccount.getAccountHistory());

        Credit savedCredit = creditRepository.save(credit);
        return creditMapper.toDto(savedCredit);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "credits", allEntries = true)
    public CreditDto deleteById(Long aLong) throws ResourceNotFoundException {
        Credit foundCredit = creditRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find Credit with id: " + aLong);
            e.setResourceName("Credit");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        creditRepository.delete(foundCredit);
        return creditMapper.toDto(foundCredit);
    }

    private Account getAccountByAccountHistoryId(Long id) throws ResourceNotFoundException {
        return accountRepository.findByAccountHistory_Id(id).orElseThrow(() ->{
            ResourceException e = new ResourceNotFoundException("Can't find AccountHistory with id: " + id);
            e.setResourceName("Credit -> AccountHistory");
            e.setFieldsAndValues(Map.of("id", id));

            return e;
        });
    }

}
