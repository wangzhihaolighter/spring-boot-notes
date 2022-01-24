package com.example.mybatis.pagehelper.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class User {
  private Long id;
  private LocalDateTime createTime;
  private String username;
  private String password;
}
