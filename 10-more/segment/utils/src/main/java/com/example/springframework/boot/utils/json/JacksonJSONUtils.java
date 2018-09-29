package com.example.springframework.boot.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

/**
 * json工具类 - Jackson实现
 */
public class JacksonJSONUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JacksonJSONUtils() {
    }

    public static <T> String parse(T obj) {
        try {
            if (obj == null) {
                return null;
            }
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            Throwables.throwIfUnchecked(e);
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> String parseInPrettyMode(T obj) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            Throwables.throwIfUnchecked(e);
            throw new IllegalArgumentException(e);
        }
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}
