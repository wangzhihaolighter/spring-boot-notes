package com.example.springframework.boot.http.encryption.encryption.aop;

import com.example.springframework.boot.http.encryption.encryption.annotation.RequestDecode;
import com.example.springframework.boot.http.encryption.encryption.constant.SystemConstants;
import com.example.springframework.boot.http.encryption.encryption.enumeration.SecurityMethod;
import com.example.springframework.boot.http.encryption.encryption.util.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * 请求参数解密
 */
@Slf4j
@Component
@ControllerAdvice(basePackages = "com.example.springframework.boot.http.encryption.web")
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {

    @Value("${system.aes.key}")
    String aesKey;
    @Value("${system.aes.type}")
    String aesType;
    @Value("${system.aes.offset}")
    String aesOffset;
    @Value("${system.aes.code-type}")
    String aesCodeType;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        //判断方法是否需要解密
        return methodParameter.hasParameterAnnotation(RequestBody.class) && methodParameter.hasMethodAnnotation(RequestDecode.class);
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage request, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage request, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        //从请求头中获取加密方法、编码方式
        String encodeMethod = request.getHeaders().getFirst(SystemConstants.HttpHeaders.ENCODE_METHOD);

        //无加密方法，不解密
        if (StringUtils.isEmpty(encodeMethod)) {
            log.info("请求未指定加密方法，不解密");
            return request;
        }

        //获取对应的解密方法，对应不同的解密方式
        SecurityMethod encodeMethodEnum = SecurityMethod.getByCode(encodeMethod);
        switch (encodeMethodEnum) {
            case NULL:
                break;
            case AES: {
                //最好在将字节流转换为字符流的时候 进行转码
                InputStream inputStream = request.getBody();
                BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = bf.readLine()) != null) {
                    buffer.append(line);
                }
                String body = buffer.toString();
                //解密
                String bodyDecrypt = AESUtil.decrypt(body, aesType, aesKey, aesOffset, aesCodeType);

                if ("".equals(bodyDecrypt)) {
                    log.error("解密失败: {}", body);
                } else {
                    log.info("解密完成: {}", bodyDecrypt);
                    return new DecodedHttpInputMessage(request.getHeaders(), new ByteArrayInputStream(bodyDecrypt.getBytes(StandardCharsets.UTF_8)));
                }
            }
            default:
                break;
        }
        return request;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    static class DecodedHttpInputMessage implements HttpInputMessage {
        HttpHeaders headers;
        InputStream body;

        DecodedHttpInputMessage(HttpHeaders headers, InputStream body) {
            this.headers = headers;
            this.body = body;
        }

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }

    public static void main(String[] args) {
        System.out.println(AESUtil.encrypt("{\"id\":1,\"name\":\"飞翔的小海狸\",\"more\":\"没有更多了\"}"));
    }

}