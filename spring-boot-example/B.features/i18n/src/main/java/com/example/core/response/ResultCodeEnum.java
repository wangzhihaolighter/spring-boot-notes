package com.example.core.response;

/**
 * 响应码枚举
 *
 * @author wangzhihao
 */
public enum ResultCodeEnum {
  /** 成功 */
  SUCCESS("0"),
  /** 失败 */
  FAIL("-1"),
  /** 请求入参校验出错 */
  REQUEST_PARAM_ERROR("1"),
  /** 鉴权出错 */
  AUTH_ERROR("2"),
  /** 未授权 */
  UNAUTHORIZED("401"),
  /** 无权限 */
  ACCESS_DENIED("403"),
  ;

  private final String code;

  ResultCodeEnum(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public static ResultCodeEnum getEnum(String code) {
    for (ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()) {
      if (resultCodeEnum.getCode().equals(code)) {
        return resultCodeEnum;
      }
    }
    return FAIL;
  }
}
