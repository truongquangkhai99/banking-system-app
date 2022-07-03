package com.unvise.bankingsystemapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DepositDoesntExistException extends RuntimeException {

    private Map<String, Object> fieldNameAndValue;

    public DepositDoesntExistException() {
        super();
    }

    public DepositDoesntExistException(String message) {
        super(message);
    }

    public DepositDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DepositDoesntExistException(Throwable cause) {
        super(cause);
    }

}
