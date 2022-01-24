package com.example.elasticsearch.entity;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "user")
public class User {
  @Id private String id;

  @Field(type = FieldType.Text)
  private String username;

  @Field(type = FieldType.Text)
  private String email;

  @Field(type = FieldType.Long)
  private Integer age;

  @Field(
      type = FieldType.Date,
      format = {},
      pattern = "uuuu-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
}
