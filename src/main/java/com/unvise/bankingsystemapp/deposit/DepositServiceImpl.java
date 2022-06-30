package com.unvise.bankingsystemapp.deposit;

import com.unvise.bankingsystemapp.account.Account;
import com.unvise.bankingsystemapp.account.AccountRepository;
import com.unvise.bankingsystemapp.deposit.web.dto.DepositDto;
import com.unvise.bankingsystemapp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final DepositRepository depositRepository;
    private final AccountRepository accountRepository;
    private final DepositMapper depositMapper;

    @Override
    public List<DepositDto> getAll() {
        List<Deposit> foundDeposits = depositRepository.findAll();
        return depositMapper.toDtoList(foundDeposits);
    }

    @Override
    public DepositDto getById(Long aLong) {
        Deposit foundDeposit = depositRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Deposit", Map.of("id", aLong)));
        return depositMapper.toDto(foundDeposit);
    }

    @Override
    public DepositDto save(DepositDto depositDto) {
        Deposit deposit = depositMapper.toEntity(depositDto);

        Account account = getAccountByAccountHistoryId(depositDto.getAccountHistoryId());
        account.getAccountHistory().setDeposit(deposit);

        Deposit savedDeposit = depositRepository.save(deposit);

        return depositMapper.toDto(savedDeposit);
    }

    @Override
    public DepositDto updateById(Long aLong, DepositDto depositDto) {
        depositRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Deposit", Map.of("id", aLong)));
        depositDto.setId(aLong);
        Deposit updatedDeposit = depositRepository.save(depositMapper.toEntity(depositDto));
        return depositMapper.toDto(updatedDeposit);
    }

    @Override
    public DepositDto deleteById(Long aLong) {
        Deposit foundDeposit = depositRepository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException("Deposit", Map.of("id", aLong)));

        Account account = getAccountByAccountHistoryId(foundDeposit.getAccountHistory().getId());
        account.getAccountHistory().setDeposit(null);

        depositRepository.delete(foundDeposit);
        return depositMapper.toDto(foundDeposit);
    }

    private Account getAccountByAccountHistoryId(Long id) {
        return accountRepository.findByAccountHistory_Id(id).orElseThrow(() ->
                new ResourceNotFoundException("AccountHistory", Map.of("id", id)));
    }

}
