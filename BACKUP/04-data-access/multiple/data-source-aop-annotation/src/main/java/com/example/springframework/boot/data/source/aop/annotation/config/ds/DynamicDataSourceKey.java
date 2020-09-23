package com.example.springframework.boot.data.source.aop.annotation.config.ds;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态数据源注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DynamicDataSourceKey {

    @AliasFor("dataSource")
    String value() default "";

    @AliasFor("value")
    String dataSource() default "";

}
