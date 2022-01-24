package com.example.transaction.interceptor.dao;

import com.example.transaction.interceptor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
