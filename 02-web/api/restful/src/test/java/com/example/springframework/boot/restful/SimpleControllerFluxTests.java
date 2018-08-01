package com.example.springframework.boot.restful;

import com.example.springframework.boot.restful.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleControllerFluxTests {

    @Autowired
    private WebTestClient webClient;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testHome() {
        this.webClient.get().uri("/flux/hello").accept(MediaType.TEXT_PLAIN).exchange()
                .expectBody(String.class).isEqualTo("Welcome to reactive world ~");
    }

    @Test
    public void testGetUserAll() throws JsonProcessingException {
        EntityExchangeResult<List<User>> listEntityExchangeResult = this.webClient.get().uri("/flux/user/all")
                .exchange().expectBodyList(User.class).hasSize(1).returnResult();
        log.info(MAPPER.writeValueAsString(listEntityExchangeResult.getResponseBody()));
    }

}
