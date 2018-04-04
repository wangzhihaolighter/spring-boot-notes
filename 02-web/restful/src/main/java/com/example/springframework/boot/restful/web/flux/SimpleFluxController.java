package com.example.springframework.boot.restful.web.flux;

import com.example.springframework.boot.restful.entity.User;
import com.example.springframework.boot.restful.repository.flux.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/flux")
public class SimpleFluxController {

    //web flux api
    //mono：单  flux：多

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/all")
    public Flux<User> getUserAll() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public Mono<User> getUserById(@PathVariable("id") Long id) {
        return userRepository.findById(id);
    }

    @PostMapping("/user/save")
    public Mono<Long> saveUser(@RequestBody User user) {
        return userRepository.save(user).thenReturn(user.getId());
    }

    @PostMapping("/user/delete")
    public Mono<Void> deleteUser(@RequestParam("id") Long id) {
        return userRepository.deleteById(id);
    }

}
