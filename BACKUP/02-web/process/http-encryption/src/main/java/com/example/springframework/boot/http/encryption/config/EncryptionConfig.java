package com.example.springframework.boot.http.encryption.config;

import com.example.springframework.boot.http.encryption.encryption.aop.DecodeRequestBodyAdvice;
import com.example.springframework.boot.http.encryption.encryption.aop.EncodeResponseBodyAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {

    /**
     * 请求解密
     */
    @Bean
    public DecodeRequestBodyAdvice decodeRequestBodyAdvice() {
        return new DecodeRequestBodyAdvice();
    }

    /**
     * 响应加密
     */
    @Bean
    public EncodeResponseBodyAdvice encodeResponseBodyAdvice() {
        return new EncodeResponseBodyAdvice();
    }

}
