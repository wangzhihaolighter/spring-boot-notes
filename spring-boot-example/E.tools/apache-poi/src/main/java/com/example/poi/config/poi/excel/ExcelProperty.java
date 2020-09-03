package com.example.poi.config.poi.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记excel要读取的属性
 *
 * @author wangzhihao
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelProperty {
    String[] value() default {""};

    int index() default -1;
}
