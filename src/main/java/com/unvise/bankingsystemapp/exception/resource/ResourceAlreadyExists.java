package com.unvise.bankingsystemapp.exception.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExists extends ResourceException{

    public ResourceAlreadyExists() {
        super();
    }

    public ResourceAlreadyExists(String message) {
        super(message);
    }

    public ResourceAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceAlreadyExists(Throwable cause) {
        super(cause);
    }

}
