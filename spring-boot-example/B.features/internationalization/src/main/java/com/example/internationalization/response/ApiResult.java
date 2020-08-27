package com.example.internationalization.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> implements Serializable {
    private String code;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private T data;

    private ApiResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(ResultCodeEnum.SUCCESS.getCode(), null, data);
    }

    public static ApiResult<Void> fail(String code, String message) {
        return new ApiResult<>(code, message, null);
    }

}
