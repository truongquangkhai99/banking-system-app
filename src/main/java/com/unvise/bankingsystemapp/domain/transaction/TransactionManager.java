package com.unvise.bankingsystemapp.domain.transaction;

import com.unvise.bankingsystemapp.domain.transaction.transaction.Transaction;
import com.unvise.bankingsystemapp.exception.transaction.TransactionFailedException;

public interface TransactionManager {

    void manage(Transaction transaction) throws TransactionFailedException;

}
