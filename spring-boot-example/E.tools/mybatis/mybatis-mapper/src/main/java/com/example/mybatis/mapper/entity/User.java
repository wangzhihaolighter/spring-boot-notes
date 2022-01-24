package com.example.mybatis.mapper.entity;

import io.mybatis.provider.Entity;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity.Table("t_user")
public class User {
  @Entity.Column(id = true)
  private Long id;

  @Entity.Column("create_time")
  private LocalDateTime createTime;

  @Entity.Column("username")
  private String username;

  @Entity.Column("password")
  private String password;
}
