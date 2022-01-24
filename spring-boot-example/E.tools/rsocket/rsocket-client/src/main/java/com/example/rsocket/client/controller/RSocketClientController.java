package com.example.rsocket.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class RSocketClientController {
  private final RSocketRequester rSocketRequester;

  public RSocketClientController(RSocketRequester rSocketRequester) {
    this.rSocketRequester = rSocketRequester;
  }

  @RequestMapping("/")
  public String send() {
    String route = "route1";
    String data = "你好，服务器！";
    Mono<String> send = rSocketRequester.route(route).data(data).retrieveMono(String.class);
    log.info("RSocket服务端响应结果 => " + send.block());
    return "发送消息成功！";
  }
}
