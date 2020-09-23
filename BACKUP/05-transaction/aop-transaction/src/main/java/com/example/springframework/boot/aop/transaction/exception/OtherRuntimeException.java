package com.example.springframework.boot.aop.transaction.exception;

public class OtherRuntimeException extends RuntimeException {
    public OtherRuntimeException() {
        super();
    }

    public OtherRuntimeException(String message) {
        super(message);
    }
}
