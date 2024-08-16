package com.codestorm.exceptionhandlingjpa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnableToProcessException extends RuntimeException{

    public UnableToProcessException(String message) {
        super(message);
    }
}
