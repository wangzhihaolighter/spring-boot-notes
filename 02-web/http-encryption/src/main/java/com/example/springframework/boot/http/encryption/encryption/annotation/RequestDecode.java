package com.example.springframework.boot.http.encryption.encryption.annotation;

import com.example.springframework.boot.http.encryption.encryption.enumeration.SecurityMethod;

import java.lang.annotation.*;

/**
 * 解密请求数据
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestDecode {

    SecurityMethod method() default SecurityMethod.NULL;

}