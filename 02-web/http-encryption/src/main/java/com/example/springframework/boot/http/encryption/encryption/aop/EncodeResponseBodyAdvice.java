package com.example.springframework.boot.http.encryption.encryption.aop;

import com.example.springframework.boot.http.encryption.encryption.annotation.ResponseEncode;
import com.example.springframework.boot.http.encryption.encryption.constant.SystemConstants;
import com.example.springframework.boot.http.encryption.encryption.enumeration.SecurityMethod;
import com.example.springframework.boot.http.encryption.encryption.util.AESUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 响应加密
 */
@Slf4j
@Component
@ControllerAdvice(basePackages = "com.example.springframework.boot.http.encryption.web")
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice {

    private static ObjectMapper MAPPER = new ObjectMapper();

    @Value("${system.aes.key}")
    String aesKey;
    @Value("${system.aes.type}")
    String aesType;
    @Value("${system.aes.offset}")
    String aesOffset;
    @Value("${system.aes.code-type}")
    String aesCodeType;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //判断方法是否需要加密
        return returnType.hasMethodAnnotation(ResponseEncode.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ResponseEncode responseEncode = returnType.getMethodAnnotation(ResponseEncode.class);
        SecurityMethod securityMethod = responseEncode.method();
        //响应不加密
        if (securityMethod == SecurityMethod.NULL) {
            log.info("响应不加密");
            return body;
        }
        //加密
        if (securityMethod == SecurityMethod.AES) {
            try {
                String temp = MAPPER.writeValueAsString(body);
                log.info("待加密数据: {}", temp);
                //加密参数可根据不同请求头中的标识动态分配
                String encodedBody = AESUtil.encrypt(temp, aesType, aesKey, aesOffset, aesCodeType);
                log.info("加密完成: {}", encodedBody);
                //响应头中携带加密参数
                response.getHeaders().set(SystemConstants.HttpHeaders.ENCODE_METHOD, SystemConstants.HttpHeaders.Encryption.AES);
                response.getHeaders().set(SystemConstants.HttpHeaders.HEADER_CONTENT_TYPE, SystemConstants.HttpHeaders.APPLICATION_BASE64_JSON_UTF8);
                return encodedBody;
            } catch (JsonProcessingException e) {
                log.error("json解析出错,e:" + e.getMessage());
            }
        }
        return body;
    }

}