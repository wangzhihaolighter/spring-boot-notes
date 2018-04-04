package com.example.springframework.boot.restful.repository;

import com.example.springframework.boot.restful.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
