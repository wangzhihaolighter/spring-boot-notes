package com.example.rsocket.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class RSocketServerController {

  @ConnectMapping
  Mono<Void> handle(RSocketRequester requester) {
    log.info("连接RSocket server success!");
    return Mono.empty();
  }

  @MessageMapping("route1")
  public String radars(String msg) {
    log.info("调用radars方法成功！收到客户端消息：" + msg);
    return "RSocket服务端已收到消息：" + msg;
  }
}
