package com.example.websocket.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * json工具类 - Jackson实现
 *
 * @author wangzhihao
 */
public class JsonUtils {
  private static final ObjectMapper MAPPER;

  static {
    // 默认的ObjectMapper
    MAPPER = new ObjectMapper();

    // 解决LocalDateTime序列化的问题
    MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    JavaTimeModule javaTimeModule = new JavaTimeModule();

    // 日期序列化
    javaTimeModule.addSerializer(
        LocalDateTime.class,
        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    javaTimeModule.addSerializer(
        LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    javaTimeModule.addSerializer(
        LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

    // 日期反序列化
    javaTimeModule.addDeserializer(
        LocalDateTime.class,
        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    javaTimeModule.addDeserializer(
        LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    javaTimeModule.addDeserializer(
        LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

    // null值不序列化
    MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    MAPPER.registerModule(javaTimeModule);
  }

  public static <T> String toJsonStr(T obj) {
    try {
      if (obj == null) {
        return null;
      }
      return MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public static <T> String toPrettyJsonStr(T obj) {
    try {
      return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public static <T> T parseJson(String jsonValue, Class<T> valueType) {
    try {
      return MAPPER.readValue(jsonValue, valueType);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public static <T> T parseJson(String jsonValue, JavaType valueType) {
    try {
      return MAPPER.readValue(jsonValue, valueType);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public static ObjectMapper getMapper() {
    return MAPPER;
  }
}
