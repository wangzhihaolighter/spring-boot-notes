package com.example.springframework.boot.swagger.repository;

import com.example.springframework.boot.swagger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
