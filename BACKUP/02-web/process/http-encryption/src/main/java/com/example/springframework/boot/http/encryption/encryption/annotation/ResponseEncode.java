package com.example.springframework.boot.http.encryption.encryption.annotation;

import com.example.springframework.boot.http.encryption.encryption.enumeration.SecurityMethod;

import java.lang.annotation.*;

/**
 * 数据响应需要加密
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseEncode {

    SecurityMethod method() default SecurityMethod.NULL;

}
