package com.example.i18n.core.exception;

import com.example.i18n.core.response.ResultCodeEnum;

import java.io.Serializable;

public class BusinessException extends RuntimeException {
  /** 错误码 */
  private final String code;

  /** 异常信息参数 */
  private final Serializable[] args;

  public BusinessException(String code, Serializable... args) {
    this.code = code;
    this.args = args;
  }

  public static void throwMessage(ResultCodeEnum resultCodeEnum, Serializable... args) {
    throw new BusinessException(resultCodeEnum.getCode(), args);
  }

  public String getCode() {
    return code;
  }

  public Serializable[] getArgs() {
    return args;
  }
}
