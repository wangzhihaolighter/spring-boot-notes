package com.example.springframework.boot.security.db.config.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class DemoResult implements Serializable {
    private int code;
    private String result;
    private String message;
    private Object data;

    public DemoResult() {
    }

    private DemoResult(int code, String result, String message, Object data) {
        this.code = code;
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public static DemoResult success(Object data) {
        return new DemoResult(ResultCodeEnum.SUCCESS.getCode(), "SUCCESS", null, data);
    }

    public static DemoResult success(Object data, String message) {
        return new DemoResult(ResultCodeEnum.SUCCESS.getCode(), "SUCCESS", message, data);
    }

    public static DemoResult fail(ResultCodeEnum resultCodeEnum) {
        return new DemoResult(resultCodeEnum.getCode(), "FAIL", resultCodeEnum.getMessage(), null);
    }

    public static DemoResult fail(ResultCodeEnum resultCodeEnum, String message) {
        return new DemoResult(resultCodeEnum.getCode(), "FAIL", message, null);
    }
}
