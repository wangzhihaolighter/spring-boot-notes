package com.example.domain;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "T_USER")
public class User {
  @Id private Long id;

  @Column("CREATE_TIME")
  private LocalDateTime createTime;

  private String username;
  private String password;
}
