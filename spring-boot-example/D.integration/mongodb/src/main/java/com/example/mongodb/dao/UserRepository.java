package com.example.mongodb.dao;

import com.example.mongodb.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    List<User> findAllByUsername(String username);
}
