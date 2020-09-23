package com.example.springframework.boot.spring.annotation.transaction.exception;

public class InsertRuntimeException extends RuntimeException {
    public InsertRuntimeException() {
        super();
    }

    public InsertRuntimeException(String message) {
        super(message);
    }
}
