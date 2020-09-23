package com.example.springframework.boot.aop.transaction.exception;

public class DeleteRuntimeException extends RuntimeException {
    public DeleteRuntimeException() {
        super();
    }

    public DeleteRuntimeException(String message) {
        super(message);
    }
}
