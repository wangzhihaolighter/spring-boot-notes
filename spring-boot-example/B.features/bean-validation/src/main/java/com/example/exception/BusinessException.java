package com.example.exception;


import com.example.response.ResultCodeEnum;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {

    private final String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public BusinessException(ResultCodeEnum resultCodeEnum) {
        this.errorCode = resultCodeEnum.getCode();
    }

    public BusinessException(String code) {
        this.errorCode = code;
    }

    public static void throwMessage(String errorCode) {
        throw new BusinessException(errorCode);
    }

    public static void throwMessage(ResultCodeEnum resultCodeEnum) {
        throw new BusinessException(resultCodeEnum.getCode());
    }

}
