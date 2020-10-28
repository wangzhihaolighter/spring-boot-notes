package com.example.okhttp.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * okhttp配置属性类
 */
@Data
@ConfigurationProperties(prefix = OkHttpProperties.PREFIX)
public class OkHttpProperties {

    /**
     * 配置属性前缀
     */
    public static final String PREFIX = "okhttp";

    /**
     * 用于标记是否自动化配置
     */
    public static final String ENABLE_KEY = "enable";

    /**
     * enabled config OkHttpClient
     */
    private boolean enable = true;

    /**
     * connectTimeout,unit seconds
     */
    private int connectTimeout;

    /**
     * writeTimeout,unit seconds
     */
    private int writeTimeout;

    /**
     * readTimeout,unit seconds
     */
    private int readTimeout;

    /**
     * proxy config
     */
    @NestedConfigurationProperty
    private Proxy proxy;

    @Data
    static class Proxy {

        /**
         * enabled use proxy
         */
        private boolean enable = false;

        /**
         * proxy type
         */
        private String type = "HTTP";

        /**
         * proxy ip
         */
        private String ip;

        /**
         * proxy port
         */
        private int port;

    }

}
