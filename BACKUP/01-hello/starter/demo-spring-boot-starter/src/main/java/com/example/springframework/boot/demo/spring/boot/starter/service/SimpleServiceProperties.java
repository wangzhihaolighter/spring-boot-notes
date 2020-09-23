package com.example.springframework.boot.demo.spring.boot.starter.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("simple.service")
public class SimpleServiceProperties {
    private String prefix;

    private String suffix;

    private Boolean enabled;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
