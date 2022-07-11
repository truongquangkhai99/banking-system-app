package com.unvise.bankingsystemapp.domain.deposit;

import com.unvise.bankingsystemapp.domain.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends BaseRepository<Deposit, Long> {
}
