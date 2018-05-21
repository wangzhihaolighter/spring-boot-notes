package com.example.springframework.boot.validation.web;

import com.example.springframework.boot.validation.config.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 未成年人
 */
@Slf4j
@RestController
@RequestMapping("/minor")
public class MinorController {

    @PostMapping("/user/save")
    public UserDTO save(@Validated({UserDTO.Minor.class}) @RequestBody UserDTO userDTO) {
        return userDTO;
    }

}
