package com.supplyhouse.invitation.exception;

public class InvitationServiceException extends RuntimeException {
    public InvitationServiceException() {
    }

    public InvitationServiceException(String s) {
        super(s);
    }

    public InvitationServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
