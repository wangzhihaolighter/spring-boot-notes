package com.example.jsch.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "remote")
public class RemoteProperties {
    private String host;
    private int port = 22;
    private String user;
    private String password;
    private String identity = "~/.ssh/id_rsa";
    private String passphrase;
}
