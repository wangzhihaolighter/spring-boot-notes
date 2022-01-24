package com.example.rsocket.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;

@Configuration
public class RSocketConfig {
  private static final String SERVER_HOST = "localhost";
  private static final int SERVER_PORT = 8000;

  @Bean
  public RSocketMessageHandler rsocketMessageHandler() {
    RSocketMessageHandler handler = new RSocketMessageHandler();
    handler.setRSocketStrategies(rsocketStrategies());
    return handler;
  }

  @Bean
  public RSocketStrategies rsocketStrategies() {
    return RSocketStrategies.builder()
        // 配置加码器
        .encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
        // 配置解码器
        .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
        // 配置路由匹配器
        .routeMatcher(new PathPatternRouteMatcher())
        .build();
  }

  @Bean
  public RSocketRequester rsocketRequester() {
    return RSocketRequester.builder().tcp(SERVER_HOST, SERVER_PORT);
  }
}
