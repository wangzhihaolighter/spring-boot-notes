package com.example.zookeeper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("zookeeper")
public class ZooKeeperProperties {
    /**
     * 逗号分隔的ip:port，每个端口对应一个zookeeper服务器
     */
    private String address;

    /**
     * session timeout in milliseconds
     */
    private int timeout;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
