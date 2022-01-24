package com.example.config.codec.annotation;

import com.example.config.codec.constant.CodecTypeEnum;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 编解码注解
 *
 * @author wangzhihao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Codec {

  /** 请求编解码方式 */
  CodecTypeEnum req() default CodecTypeEnum.NULL;

  /** 响应编解码方式 */
  CodecTypeEnum res() default CodecTypeEnum.NULL;
}
