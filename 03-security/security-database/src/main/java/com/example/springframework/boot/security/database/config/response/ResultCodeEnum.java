package com.example.springframework.boot.security.database.config.response;

public enum ResultCodeEnum {
    //成功
    SUCCESS(0,null),
    //失败
    FAIL(-1,"系统出错"),
    //未知,
    NOT_FOUND(1,"未找到"),
    //需要登录
    AUTH_LOGIN(2,"需要认证，请登录"),
    //认证登录失败
    AUTH_ERROR(2,"认证失败，请检查登录信息或联系管理员")
    ;

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResultCodeEnum getErrorCodeEnum(int errorCode) {
        for (ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()) {
            if (resultCodeEnum.getCode() == errorCode) {
                return resultCodeEnum;
            }
        }
        return null;
    }
}
