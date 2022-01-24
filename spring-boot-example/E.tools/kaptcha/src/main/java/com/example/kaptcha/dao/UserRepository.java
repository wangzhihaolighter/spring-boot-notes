package com.example.kaptcha.dao;

import com.example.kaptcha.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findUsersByUsernameAndPassword(String username, String password);
}
