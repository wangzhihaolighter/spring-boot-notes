package com.example.core.exception;

import com.example.core.response.ResultCodeEnum;

import java.io.Serializable;

/**
 * 业务异常类
 *
 * @author wangzhihao
 */
public class BusinessException extends RuntimeException {
  /** 错误码 */
  private final String code;

  /** 异常信息参数 */
  private final Object[] args;

  public String getCode() {
    return code;
  }

  public Object[] getArgs() {
    return args;
  }

  public BusinessException(String code, Serializable... args) {
    this.code = code;
    this.args = args;
  }

  public static void throwMessage(ResultCodeEnum resultCodeEnum, Serializable... args) {
    throw new BusinessException(resultCodeEnum.getCode(), args);
  }
}
