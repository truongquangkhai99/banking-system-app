package com.unvise.bankingsystemapp.transaction.transaction;

import com.unvise.bankingsystemapp.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Long> {
}
