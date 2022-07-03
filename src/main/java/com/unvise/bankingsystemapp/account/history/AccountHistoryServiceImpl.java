package com.unvise.bankingsystemapp.account.history;

import com.unvise.bankingsystemapp.account.account.Account;
import com.unvise.bankingsystemapp.account.web.dto.AccountHistoryDto;
import com.unvise.bankingsystemapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountHistoryServiceImpl implements AccountHistoryService {

    private final AccountHistoryRepository accountHistoryRepository;
    private final AccountHistoryMapper accountHistoryMapper;

    @Override
    public List<AccountHistoryDto> getAll() {
        List<AccountHistory> foundAccountHistories = accountHistoryRepository.findAll();
        return accountHistoryMapper.toDtoList(foundAccountHistories);
    }

    @Override
    public AccountHistoryDto getById(Long aLong) {
        AccountHistory foundAccountHistory = accountHistoryRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("AccountHistory", Map.of("id", aLong)));
        return accountHistoryMapper.toDto(foundAccountHistory);
    }

    // todo: complete this methods
    @Override
    public AccountHistoryDto save(AccountHistoryDto accountHistoryDto) {
        return null;
    }

    @Override
    public AccountHistoryDto updateById(Long aLong, AccountHistoryDto accountHistoryDto) {
        return null;
    }

    @Override
    public AccountHistoryDto deleteById(Long aLong) {
        return null;
    }

}
