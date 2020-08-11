package com.example.jsch.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    /**
     * 上传基路径
     */
    private String uploadBasePath;
}
