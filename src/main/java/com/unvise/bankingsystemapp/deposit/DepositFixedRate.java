package com.unvise.bankingsystemapp.deposit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DepositFixedRate {

    private final DepositRepository depositRepository;

    @Scheduled(cron = "*/60 * * * * *")
    private void insertNewDepositBalance() {
        List<Deposit> deposits = depositRepository.findAll();

        if (deposits.isEmpty()) {
            log.debug("No deposits for update");
            return;
        }

        deposits.forEach(deposit -> deposit.addToBalance(
                findNewBalanceDeposit(deposit.getBalance(), deposit.getIntenseRate())
        ));

        depositRepository.saveAll(deposits);

        log.info("Update deposits balance");
    }

    private BigDecimal findNewBalanceDeposit(BigDecimal balance, BigDecimal intenseRate) {
        return balance.multiply(intenseRate.subtract(BigDecimal.ONE));
    }

}
