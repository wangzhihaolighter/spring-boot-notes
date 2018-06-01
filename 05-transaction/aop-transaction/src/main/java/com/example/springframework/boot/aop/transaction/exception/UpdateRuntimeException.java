package com.example.springframework.boot.aop.transaction.exception;

public class UpdateRuntimeException extends RuntimeException {
    public UpdateRuntimeException() {
        super();
    }

    public UpdateRuntimeException(String message) {
        super(message);
    }
}
