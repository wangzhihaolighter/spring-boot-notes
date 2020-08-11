package com.example.okhttp.spring.boot.starter;

import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * OkHttp自动配置类
 */
@Configuration
@ConditionalOnClass(OkHttpClient.class)
@EnableConfigurationProperties(OkHttpProperties.class)
public class OkHttpAutoConfiguration {

    private final OkHttpProperties properties;

    public OkHttpAutoConfiguration(OkHttpProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = OkHttpProperties.PREFIX, value = OkHttpProperties.ENABLE_KEY, havingValue = "true")
    OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //设置代理
        if (properties.getProxy() != null && properties.getProxy().isEnable()) {
            builder.proxy(
                    new Proxy(Proxy.Type.valueOf(properties.getProxy().getType()),
                            new InetSocketAddress(properties.getProxy().getIp(), properties.getProxy().getPort()))
            );
        }

        builder.retryOnConnectionFailure(true)
                .connectTimeout(properties.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(properties.getWriteTimeout(), TimeUnit.SECONDS)
                .readTimeout(properties.getReadTimeout(), TimeUnit.SECONDS)
        ;

        return builder.build();
    }

}
