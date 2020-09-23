package com.example.springframework.boot.spring.annotation.transaction.exception;

public class UpdateRuntimeException extends RuntimeException {
    public UpdateRuntimeException() {
        super();
    }

    public UpdateRuntimeException(String message) {
        super(message);
    }
}
