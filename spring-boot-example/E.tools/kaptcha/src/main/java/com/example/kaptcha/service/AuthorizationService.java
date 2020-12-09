package com.example.kaptcha.service;

import com.example.kaptcha.dao.UserRepository;
import com.example.kaptcha.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class AuthorizationService {

    private final UserRepository userRepository;

    public User findUser(String username, String password) {
        return userRepository.findUsersByUsernameAndPassword(username, password);
    }

}
