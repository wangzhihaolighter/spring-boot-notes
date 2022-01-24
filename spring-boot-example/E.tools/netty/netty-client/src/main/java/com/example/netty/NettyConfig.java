package com.example.netty;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyConfig {
  private final NettyServerProperties nettyServerProperties;

  public NettyConfig(NettyServerProperties nettyServerProperties) {
    this.nettyServerProperties = nettyServerProperties;
  }

  @Bean
  public WebSocketClientProtocolConfig webSocketClientProtocolConfig() {
    return WebSocketClientProtocolConfig.newBuilder()
        .webSocketUri(
            String.format(
                "ws://%s:%s%s",
                nettyServerProperties.getIp(),
                nettyServerProperties.getPort(),
                nettyServerProperties.getPath()))
        .version(WebSocketVersion.V13)
        .customHeaders(new DefaultHttpHeaders())
        .build();
  }
}
