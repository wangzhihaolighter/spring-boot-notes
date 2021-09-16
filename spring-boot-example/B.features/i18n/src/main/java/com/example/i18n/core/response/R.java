package com.example.i18n.core.response;

import com.example.i18n.core.util.MessageUtils;
import com.example.i18n.core.util.ServletUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Locale;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> implements Serializable {
  private String code;
  private String msg;
  private T data;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private LocalDateTime timestamp;

  private R(String code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
    this.timestamp = LocalDateTime.now();
  }

  public static R<Void> success() {
    return result(ResultCodeEnum.SUCCESS, null);
  }

  public static <T> R<T> success(T data) {
    return result(ResultCodeEnum.SUCCESS, data);
  }

  public static <T> R<T> fail(ResultCodeEnum resultCodeEnum) {
    return fail(resultCodeEnum, null);
  }

  public static <T> R<T> fail(ResultCodeEnum resultCodeEnum, T data) {
    return fail(resultCodeEnum, null, data);
  }

  public static <T> R<T> fail(ResultCodeEnum resultCodeEnum, Object[] args, T data) {
    return result(resultCodeEnum, args, data);
  }

  public static <T> R<T> result(ResultCodeEnum resultCodeEnum, T data) {
    return result(resultCodeEnum, null, data);
  }

  public static <T> R<T> result(ResultCodeEnum resultCodeEnum, Object[] args, T data) {
    final Locale locale = RequestContextUtils.getLocale(ServletUtils.getRequest());
    final String code = resultCodeEnum.getCode();
    final String message = MessageUtils.getMessage(code, args, locale);
    return result(code, message, data);
  }

  public static <T> R<T> result(String code, String message, T data) {
    return new R<>(code, message, data);
  }
}
