package com.example.springframework.boot.exception.config.exception;

import com.example.springframework.boot.exception.config.response.ResultCodeEnum;

public class SystemErrorException extends Exception {

    private ResultCodeEnum resultCodeEnum;

    public SystemErrorException() {
        super();
    }

    public SystemErrorException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
    }

    public ResultCodeEnum getResultCodeEnum() {
        return resultCodeEnum;
    }

    public void setResultCodeEnum(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
    }
}