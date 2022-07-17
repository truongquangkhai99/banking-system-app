package com.unvise.bankingsystemapp.domain.account.history;

import com.unvise.bankingsystemapp.domain.account.web.dto.AccountHistoryDto;
import com.unvise.bankingsystemapp.exception.impl.NotImplementedException;
import com.unvise.bankingsystemapp.exception.resource.ResourceException;
import com.unvise.bankingsystemapp.exception.resource.ResourceNotFoundException;
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
    public AccountHistoryDto getById(Long aLong) throws ResourceNotFoundException {
        AccountHistory foundAccountHistory = accountHistoryRepository.findById(aLong).orElseThrow(() -> {
            ResourceException e = new ResourceNotFoundException("Can't find AccountHistory with id: " + aLong);
            e.setResourceName("Account History");
            e.setFieldsAndValues(Map.of("id", aLong));

            return e;
        });

        return accountHistoryMapper.toDto(foundAccountHistory);
    }

    /*
        Notification: These methods have no implementation because they should not be used anywhere.
        The history will be updated if, add, update or delete (transaction(s), credit(s), deposit).
    */
    @Override
    public AccountHistoryDto save(AccountHistoryDto accountHistoryDto) throws NotImplementedException {
        throw new NotImplementedException("This method has no implementation because it should not be used");
    }

    @Override
    public AccountHistoryDto updateById(Long aLong, AccountHistoryDto accountHistoryDto) throws NotImplementedException {
        throw new NotImplementedException("This method has no implementation because it should not be used");
    }

    @Override
    public AccountHistoryDto deleteById(Long aLong) throws NotImplementedException {
        throw new NotImplementedException("This method has no implementation because it should not be used");
    }

}
