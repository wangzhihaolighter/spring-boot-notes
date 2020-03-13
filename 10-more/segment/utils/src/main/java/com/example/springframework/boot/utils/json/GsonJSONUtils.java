package com.example.springframework.boot.utils.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * json工具类 - Jackson实现
 */
public class GsonJSONUtils {
    private static Gson gson;

    static {
        gson = new Gson();
    }

    private GsonJSONUtils() {
    }

    /**
     * 对象转成json
     *
     * @param object 对象
     * @return json
     */
    public static <T> String jsonString(T object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * Json转成对象
     *
     * @param jsonString json字符串
     * @param cls        转换的对象类
     * @return 对象
     */
    public static <T> T jsonToBean(String jsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(jsonString, cls);
        }
        return t;
    }

    /**
     * json转成list<T>
     *
     * @param jsonString json字符串
     * @param cls        对象类
     * @return list<T>
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * json转成map集合
     *
     * @param jsonString json字符串
     * @return List<Map<String, T>>
     */
    public static <T> List<Map<String, T>> jsonToListMaps(String jsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(jsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return list;
    }

    /**
     * json转成map
     *
     * @param jsonString json字符串
     * @return Map<String, T>
     */
    public static <T> Map<String, T> jsonToMaps(String jsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(jsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
