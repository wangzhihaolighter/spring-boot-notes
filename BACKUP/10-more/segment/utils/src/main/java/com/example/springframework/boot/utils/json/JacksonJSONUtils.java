package com.example.springframework.boot.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * json工具类 - Jackson实现
 */
public class JacksonJSONUtils {
    private static final ObjectMapper MAPPER;

    static {
        // 默认的ObjectMapper
        MAPPER = new ObjectMapper();
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

    public static <T> T parseJson(String jsonValue, Class<T> valueType) throws IOException {
        return MAPPER.readValue(jsonValue, valueType);
    }

    public static <T> T parseJson(String jsonValue, JavaType valueType) throws IOException {
        return MAPPER.readValue(jsonValue, valueType);
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}
