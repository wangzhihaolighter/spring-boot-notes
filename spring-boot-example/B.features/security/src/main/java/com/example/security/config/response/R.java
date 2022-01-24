package com.example.security.config.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 自定义响应
 *
 * @param <T> data类型
 * @author wangzhihao
 */
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

  public static <T> R<T> success(T data) {
    return result("00", "成功", data);
  }

  public static R<Void> result(String code, String message) {
    return result(code, message, null);
  }

  public static <T> R<T> result(String code, String message, T data) {
    return new R<>(code, message, data);
  }
}
