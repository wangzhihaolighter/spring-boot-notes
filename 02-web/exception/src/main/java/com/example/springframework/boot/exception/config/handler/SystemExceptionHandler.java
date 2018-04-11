package com.example.springframework.boot.exception.config.handler;

import com.example.springframework.boot.exception.config.exception.CustomErrorException;
import com.example.springframework.boot.exception.config.exception.SystemErrorException;
import com.example.springframework.boot.exception.config.response.DemoResult;
import com.example.springframework.boot.exception.config.response.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class SystemExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public DemoResult defaultErrorHandler(Exception e) {
        return DemoResult.fail(ResultCodeEnum.FAIL, "内部错误:" + e.getMessage());
    }

    @ExceptionHandler(value = SystemErrorException.class)
    public DemoResult systemErrorHandler(SystemErrorException e) {
        ResultCodeEnum resultCodeEnum = e.getResultCodeEnum();
        return DemoResult.fail(resultCodeEnum, "系统定义错误:" + resultCodeEnum.getErrorMessage());
    }

    @ExceptionHandler(value = CustomErrorException.class)
    public DemoResult systemErrorHandler(CustomErrorException e) {
        ResultCodeEnum resultCodeEnum = e.getResultCodeEnum();
        return DemoResult.fail(resultCodeEnum, "自定义错误:" + e.getMessage());
    }

}