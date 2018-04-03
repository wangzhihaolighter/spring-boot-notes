package com.example.springframework.boot.webflux.web.flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/flux")
public class SimpleFluxController {

    @GetMapping("/")
    public Mono<String> home(){
        return Mono.just("hello,web flux");
    }

}
