package com.example.mongodb.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;

@Data
@TypeAlias(("user"))
@Document("user")
public class User implements Serializable {
    @MongoId
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String username;
    private String password;
}