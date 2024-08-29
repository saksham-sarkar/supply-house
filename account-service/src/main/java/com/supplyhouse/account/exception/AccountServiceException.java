package com.supplyhouse.account.exception;

public class AccountServiceException extends RuntimeException {
    public AccountServiceException() {
    }

    public AccountServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AccountServiceException(String s) {
        super(s);
    }
}
