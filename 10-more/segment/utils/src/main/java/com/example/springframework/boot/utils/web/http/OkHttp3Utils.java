package com.example.springframework.boot.utils.web.http;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * HTTP工具类，使用OkHttp实现
 */
public class OkHttp3Utils {

    /**
     * 懒汉 安全 加同步
     * 私有的静态成员变量 只声明不创建
     * 私有的构造方法
     * 提供返回实例的静态方法
     */
    private static OkHttpClient okHttpClient = null;

    private OkHttp3Utils() {
    }

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            //加同步安全
            synchronized (OkHttp3Utils.class) {
                if (okHttpClient == null) {
                    //缺省 - 带有连接池
                    return new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true)
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .build();
                }
            }

        }
        return okHttpClient;
    }

    //TODO

}
