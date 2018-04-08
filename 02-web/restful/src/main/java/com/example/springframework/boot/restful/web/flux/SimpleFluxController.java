package com.example.springframework.boot.restful.web.flux;

import com.example.springframework.boot.restful.entity.User;
import com.example.springframework.boot.restful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/flux")
public class SimpleFluxController {

    //web flux api
    //mono：单  flux：多

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public Mono<String> home() {
        return Mono.just("Welcome to reactive world ~");
    }

    @GetMapping("/user/all")
    public Flux<User> getUserAll() {
        return Flux.create(userFluxSink -> {
                    userRepository.findAll().forEach(userFluxSink::next);
                    //需要手动结束
                    userFluxSink.complete();
                }
        );
    }

    @GetMapping("/user/{id}")
    public Mono<User> getUserById(@PathVariable("id") Long id) {
        return Mono.create(userMonoSink -> userMonoSink.success(userRepository.findById(id).get()));
    }

    @PostMapping("/user/save")
    public Mono<Long> saveUser(@RequestBody User user) {
        return Mono.create(longMonoSink -> longMonoSink.success(userRepository.save(user).getId()));
    }

    @PostMapping("/user/delete")
    public Mono<Void> deleteUser(@RequestParam("id") Long id) {
        return Mono.create(voidMonoSink -> {
            userRepository.deleteById(id);
            voidMonoSink.success();
        });
    }

}
