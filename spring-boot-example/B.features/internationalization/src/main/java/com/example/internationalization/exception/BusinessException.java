package com.example.internationalization.exception;


import com.example.internationalization.response.ResultCodeEnum;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {

    private final String resultCode;

    private final Object[] args;

    public String getResultCode() {
        return resultCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public BusinessException(ResultCodeEnum resultCodeEnum, Object[] args) {
        this.resultCode = resultCodeEnum.getCode();
        this.args = args;
    }

    public BusinessException(String code, Object[] args) {
        this.resultCode = code;
        this.args = args;
    }

    public static void throwMessage(String errorCode, Object[] args) {
        throw new BusinessException(errorCode, args);
    }

    public static void throwMessage(ResultCodeEnum resultCodeEnum, Object[] args) {
        throw new BusinessException(resultCodeEnum.getCode(), args);
    }

}
