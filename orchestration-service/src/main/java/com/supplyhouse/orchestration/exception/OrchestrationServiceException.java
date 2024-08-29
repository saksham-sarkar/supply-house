package com.supplyhouse.orchestration.exception;

public class OrchestrationServiceException extends RuntimeException{
    public OrchestrationServiceException() {
    }

    public OrchestrationServiceException(String s) {
        super(s);
    }

    public OrchestrationServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
