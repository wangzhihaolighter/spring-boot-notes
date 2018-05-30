package com.example.springframework.boot.transaction.exception;

public class UpdateRuntimeException extends RuntimeException {
    public UpdateRuntimeException() {
        super();
    }

    public UpdateRuntimeException(String message) {
        super(message);
    }
}
