package com.example.config.codec.aop;

import com.example.config.codec.annotation.Codec;
import com.example.config.codec.constant.CodecTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/** 响应信息编解码处理 */
@Slf4j
@Component
@ControllerAdvice(basePackages = "com.example.controller")
public class CodecResponseBodyAdvice implements ResponseBodyAdvice {

  private final ObjectMapper objectMapper;

  public CodecResponseBodyAdvice(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    return returnType.hasMethodAnnotation(Codec.class);
  }

  @SneakyThrows
  @Override
  public Object beforeBodyWrite(
      Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {
    if (body == null) {
      return null;
    }

    // 编解码方式
    Codec codec = returnType.getMethodAnnotation(Codec.class);
    if (Objects.isNull(codec)) {
      return body;
    }

    // 未指定编解码方式
    CodecTypeEnum reqCodecType = codec.res();
    if (Objects.equals(reqCodecType, CodecTypeEnum.NULL)) {
      return body;
    }

    // 编码处理
    switch (reqCodecType) {
      case BASE64:
        log.info("base64 encode ...");
        String temp = objectMapper.writeValueAsString(body);
        log.info("待编码数据: {}", temp);
        String encodeStr =
            Base64.encodeBase64String(StringUtils.getBytes(temp, StandardCharsets.UTF_8));
        log.info("编码后数据: {}", encodeStr);
        body = encodeStr;
        break;
      case AES:
        log.info("aes encrypt ...");
        break;
      default:
        log.info("no codec");
        break;
    }

    // 加个编码方式响应头参数
    response.getHeaders().set("codec", reqCodecType.name());
    return body;
  }
}
