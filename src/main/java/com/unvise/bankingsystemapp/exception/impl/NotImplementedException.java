package com.unvise.bankingsystemapp.exception.impl;

import com.unvise.bankingsystemapp.exception.BaseException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotImplementedException extends BaseException {

    public NotImplementedException() {
        super();
    }

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotImplementedException(Throwable cause) {
        super(cause);
    }

}
