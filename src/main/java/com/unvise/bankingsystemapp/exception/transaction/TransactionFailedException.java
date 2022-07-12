package com.unvise.bankingsystemapp.exception.transaction;

import com.unvise.bankingsystemapp.domain.transaction.transaction.Transaction;
import com.unvise.bankingsystemapp.exception.BaseException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionFailedException extends BaseException {

    public TransactionFailedException() {
        super();
    }

    public TransactionFailedException(String message) {
        super(message);
    }

    public TransactionFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionFailedException(Throwable cause) {
        super(cause);
    }
}
