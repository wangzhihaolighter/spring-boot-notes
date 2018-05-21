package com.example.springframework.boot.validation.web;

import com.example.springframework.boot.validation.config.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 成年人
 */
@Slf4j
@RestController
@RequestMapping("/adult")
public class AdultController {

    @PostMapping("/user/save")
    public UserDTO save(@Validated({UserDTO.Adult.class}) @RequestBody UserDTO userDTO) {
        return userDTO;
    }

}
