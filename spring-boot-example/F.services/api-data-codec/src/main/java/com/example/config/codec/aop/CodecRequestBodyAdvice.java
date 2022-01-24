package com.example.config.codec.aop;

import com.example.config.codec.annotation.Codec;
import com.example.config.codec.constant.CodecTypeEnum;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

/**
 * 请求信息编解码处理
 *
 * @author wangzhihao
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
 */
@Slf4j
@Component
@ControllerAdvice(basePackages = "com.example.controller")
public class CodecRequestBodyAdvice implements RequestBodyAdvice {

  @Override
  public boolean supports(
      MethodParameter methodParameter,
      Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return methodParameter.hasMethodAnnotation(Codec.class);
  }

  @Override
  public HttpInputMessage beforeBodyRead(
      HttpInputMessage request,
      MethodParameter parameter,
      Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType)
      throws IOException {
    // 编解码方式
    Codec codec = parameter.getMethodAnnotation(Codec.class);
    if (Objects.isNull(codec)) {
      return request;
    }

    // 未指定编解码方式
    CodecTypeEnum reqCodecType = codec.req();
    if (Objects.equals(reqCodecType, CodecTypeEnum.NULL)) {
      return request;
    }

    // 解码处理
    switch (reqCodecType) {
      case BASE64:
        log.info("base64 decode ...");
        HttpInputMessage requestTemp = request;
        request =
            new HttpInputMessage() {
              @Override
              public HttpHeaders getHeaders() {
                return requestTemp.getHeaders();
              }

              @Override
              public InputStream getBody() throws IOException {
                byte[] bytes = IOUtils.toByteArray(requestTemp.getBody());
                byte[] decodeBase64 = Base64.decodeBase64(bytes);
                return new ByteArrayInputStream(decodeBase64);
              }
            };
        break;
      case AES:
        log.info("aes decrypt ...");
        break;
      default:
        log.info("no codec");
        break;
    }
    return request;
  }

  @Override
  public Object afterBodyRead(
      Object body,
      HttpInputMessage inputMessage,
      MethodParameter parameter,
      Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return body;
  }

  @Override
  public Object handleEmptyBody(
      Object body,
      HttpInputMessage request,
      MethodParameter parameter,
      Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return body;
  }
}
