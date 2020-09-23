package com.example.springframework.boot.dynamic.quartz.scheduler.config.okhttp3;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttp3ClientConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        //缺省 - 带有连接池
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

}
