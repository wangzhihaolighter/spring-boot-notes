package com.example.netty;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("netty.server")
public class NettyServerProperties {
  private String ip;
  private Integer port;
  private String path;
}
