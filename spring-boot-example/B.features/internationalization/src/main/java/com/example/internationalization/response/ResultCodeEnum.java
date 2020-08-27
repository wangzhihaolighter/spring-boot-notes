package com.example.internationalization.response;

public enum ResultCodeEnum {
    //成功
    SUCCESS("00"),
    //失败
    FAIL("99"),
    //未找到
    NOT_FOUND("98"),
    //请求入参校验出错
    REQUEST_PARAM_ERROR("97");

    private final String code;

    public String getCode() {
        return code;
    }

    ResultCodeEnum(String code) {
        this.code = code;
    }

    public static ResultCodeEnum getEnum(String code) {
        for (ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()) {
            if (resultCodeEnum.getCode().equals(code)) {
                return resultCodeEnum;
            }
        }
        return null;
    }

}
