package com.example.springframework.boot.transaction.jta.user.feign;

import com.example.springframework.boot.transaction.jta.user.feign.dto.People;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient("people-service")
public interface PeopleSimpleClient {

    @GetMapping("/")
    String home();

    /* read */

    @GetMapping("/people/all")
    List<People> findAll();

    /* write */

    @PostMapping("/people/save/1")
    void saveSuccess();

    @PostMapping("/people/save/3")
    void saveSuccessTransactional();

    @DeleteMapping("/people/delete")
    void clean();
}
