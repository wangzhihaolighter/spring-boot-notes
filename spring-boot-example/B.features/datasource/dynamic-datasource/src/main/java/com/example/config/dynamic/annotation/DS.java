package com.example.config.dynamic.annotation;

import com.example.config.dynamic.DynamicDataSourceConstant;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态数据源注解
 *
 * @author wangzhihao
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DS {

  /**
   * 指定动态数据源KEY
   *
   * @return 数据源KEY
   */
  String value() default DynamicDataSourceConstant.DEFAULT_KEY;
}
