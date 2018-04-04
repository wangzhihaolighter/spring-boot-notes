package com.example.springframework.boot.webflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebfluxApplicationTests {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testWelcome() {
        this.webClient.get().uri("/").accept(MediaType.TEXT_PLAIN).exchange()
                .expectBody(String.class).isEqualTo("Hello World");
    }

    @Test
    public void testEcho() {
        this.webClient.post().uri("/echo").contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.TEXT_PLAIN)
                .body(Mono.just("Hello WebFlux!"), String.class).exchange()
                .expectBody(String.class).isEqualTo("Hello WebFlux!");
    }

}
