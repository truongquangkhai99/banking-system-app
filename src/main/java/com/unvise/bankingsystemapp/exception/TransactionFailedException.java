package com.unvise.bankingsystemapp.exception;

import com.unvise.bankingsystemapp.domain.transaction.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TransactionFailedException extends RuntimeException {

    protected Transaction transaction;
    private Map<String, Object> fieldNameAndValue;

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
