package com.unvise.bankingsystemapp.exception.resource;

import com.unvise.bankingsystemapp.exception.BaseException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceException extends BaseException {

    protected String resourceName;

    public ResourceException() {
        super();
    }

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceException(Throwable cause) {
        super(cause);
    }

}
