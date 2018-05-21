package com.example.springframework.boot.validation.config.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class DemoResult<T> implements Serializable {
    private int code;
    private String result;
    private String message;
    private T data;

    public DemoResult() {
    }

    private DemoResult(int code, String result, String message, T data) {
        this.code = code;
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public  DemoResult<T> success(T data) {
        return new DemoResult<>(ResultCodeEnum.SUCCESS.getErrorCode(),"SUCCESS",null,data);
    }

    public static DemoResult fail(ResultCodeEnum resultCodeEnum, String message) {
        return new DemoResult<>(resultCodeEnum.getErrorCode(),"FAIL",message,null);
    }
}
