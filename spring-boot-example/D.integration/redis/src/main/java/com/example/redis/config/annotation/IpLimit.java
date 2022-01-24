package com.example.redis.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IP限流
 *
 * @author wangzhihao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IpLimit {
  // 资源 key
  String key() default "";

  // 资源名称，用于描述功能
  String name() default "";

  // 频次统计时间，单位：秒
  int duration();

  // 统计时间内最大速率
  int maxRate();
}
