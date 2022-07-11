package com.unvise.bankingsystemapp.exception.token;

import com.unvise.bankingsystemapp.exception.BaseException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenException extends BaseException {

    public JwtTokenException() {
        super();
    }

    public JwtTokenException(String message) {
        super(message);
    }

    public JwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenException(Throwable cause) {
        super(cause);
    }

}
