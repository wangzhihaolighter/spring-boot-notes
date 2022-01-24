package com.example.mapstruct.config.dto;

import lombok.Data;

@Data
public class UserDTO {
  private Long id;
  private String username;
  private String password;
  private String tel;
  private String mail;
}
