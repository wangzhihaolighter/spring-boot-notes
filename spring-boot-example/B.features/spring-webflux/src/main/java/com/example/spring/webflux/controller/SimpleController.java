package com.example.spring.webflux.controller;

import com.example.spring.webflux.hello.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SimpleController {

  @GetMapping("/")
  public Mono<String> hello() {
    return Mono.just("Hello,World!");
  }

  @GetMapping("/mono")
  public Mono<Greeting> monoTest() {
    return Mono.create(sink -> sink.success(new Greeting("mono")));
  }

  @GetMapping("/flux")
  public Flux<Greeting> fluxTest() {
    return Flux.create(
        sink -> {
          sink.next(new Greeting("flux1"));
          sink.next(new Greeting("flux2"));
          sink.next(new Greeting("flux3"));
          sink.complete();
        });
  }
}
