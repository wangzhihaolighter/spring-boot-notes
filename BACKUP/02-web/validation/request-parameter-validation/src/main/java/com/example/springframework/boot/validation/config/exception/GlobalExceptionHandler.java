package com.example.springframework.boot.validation.config.exception;

import com.example.springframework.boot.validation.config.response.DemoResult;
import com.example.springframework.boot.validation.config.response.ResultCodeEnum;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * 方法接收参数校验异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DemoResult handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder message = new StringBuilder(ResultCodeEnum.REQUEST_PARAM_ERROR.getErrorMessage() + ":");
        allErrors.forEach(error -> message.append(error.getDefaultMessage()).append(";"));
        return DemoResult.fail(ResultCodeEnum.REQUEST_PARAM_ERROR, message.toString());
    }

}