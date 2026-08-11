package com.tss.loanEmiSchedular.exception;

import org.springframework.http.HttpStatus;

public class UserApiException extends ApplicationException {

    public UserApiException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public UserApiException(String message, HttpStatus status) {
        super(message, status);
    }
}
