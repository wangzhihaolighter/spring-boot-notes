package com.example.jjwt.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置类
 */
@Component
@ConfigurationProperties(prefix = JwtProperties.PROPERTIES_PREFIX)
public class JwtProperties {

    public static final String PROPERTIES_PREFIX = "jwt";

    /**
     * JWT请求头参数
     */
    private String header;

    /**
     * 令牌前缀
     */
    private String tokenStartWith;

    /**
     * 签名密钥，最小32位
     */
    private String signingKey;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenStartWith() {
        return tokenStartWith;
    }

    public void setTokenStartWith(String tokenStartWith) {
        this.tokenStartWith = tokenStartWith;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }
}