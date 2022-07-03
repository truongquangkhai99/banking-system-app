package com.unvise.bankingsystemapp.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@Getter
@Setter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ExchangeRateAlreadyExistException extends RuntimeException {

    private Map<String, Object> fieldNameAndValue;

    public ExchangeRateAlreadyExistException() {
        super();
    }

    public ExchangeRateAlreadyExistException(String message) {
        super(message);
    }

    public ExchangeRateAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExchangeRateAlreadyExistException(Throwable cause) {
        super(cause);
    }

}
