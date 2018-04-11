package com.example.springframework.boot.exception.config.exception;

import com.example.springframework.boot.exception.config.response.ResultCodeEnum;

public class CustomErrorException extends Exception {

    private ResultCodeEnum resultCodeEnum;

    private String message;

    public CustomErrorException() {
        super();
    }

    public CustomErrorException(String message, ResultCodeEnum resultCodeEnum) {
        super(message);
        this.resultCodeEnum = resultCodeEnum;
        this.message = message;
    }

    public ResultCodeEnum getResultCodeEnum() {
        return resultCodeEnum;
    }

    public void setResultCodeEnum(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
    }
}