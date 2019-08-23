package com.example.okhttp.spring.boot.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * okhttp配置类
 */
@Data
@ConfigurationProperties(prefix = OkHttpProperties.OKHTTP_PREFIX)
public class OkHttpProperties {

    public static final String OKHTTP_PREFIX = "okhttp";
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
    private Proxy proxy;

    @Data
    class Proxy {
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
