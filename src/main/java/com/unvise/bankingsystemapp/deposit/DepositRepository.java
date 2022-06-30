package com.unvise.bankingsystemapp.deposit;

import com.unvise.bankingsystemapp.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends BaseRepository<Deposit, Long> {
}
