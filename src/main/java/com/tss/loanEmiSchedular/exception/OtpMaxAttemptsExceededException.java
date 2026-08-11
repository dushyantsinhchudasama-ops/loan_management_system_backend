package com.tss.loanEmiSchedular.exception;

public class OtpMaxAttemptsExceededException extends RuntimeException {

    public OtpMaxAttemptsExceededException(String message) {
        super(message);
    }
}
