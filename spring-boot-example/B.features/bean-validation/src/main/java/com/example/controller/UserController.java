package com.example.controller;

import com.example.dto.UserDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("/all")
    public List<UserDTO> getUserAll() {
        return null;
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        return null;
    }

    @PostMapping("/save")
    public Long saveUser(@Validated @RequestBody UserDTO user) {
        System.out.println(user);
        return 1L;
    }

    @DeleteMapping("/delete")
    public void deleteUser(@NotNull @RequestParam("id") Long id) {
    }

}
