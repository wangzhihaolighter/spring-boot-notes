package com.example.spring.webflux;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.spring.webflux.hello.Greeting;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
class SpringWebfluxApplicationTests {

  @Test
  public void testHello() {
    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    Mono<String> mono =
        webClient
            .get()
            .uri("/hello")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Greeting.class)
            .map(Greeting::getMessage);
    mono.doOnNext(result -> assertEquals("Hello, Spring!", result)).subscribe();
  }
}
