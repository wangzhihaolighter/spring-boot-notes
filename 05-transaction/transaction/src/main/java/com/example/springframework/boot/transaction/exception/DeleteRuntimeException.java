package com.example.springframework.boot.transaction.exception;

public class DeleteRuntimeException extends RuntimeException {
    public DeleteRuntimeException() {
        super();
    }

    public DeleteRuntimeException(String message) {
        super(message);
    }
}
