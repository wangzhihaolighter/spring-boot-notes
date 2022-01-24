package com.example.transaction.annotation.dao;

import com.example.transaction.annotation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
