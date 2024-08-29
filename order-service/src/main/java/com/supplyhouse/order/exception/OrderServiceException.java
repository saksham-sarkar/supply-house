package com.supplyhouse.order.exception;

public class OrderServiceException extends RuntimeException {
    public OrderServiceException() {
    }

    public OrderServiceException(String s) {
        super(s);
    }

    public OrderServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
