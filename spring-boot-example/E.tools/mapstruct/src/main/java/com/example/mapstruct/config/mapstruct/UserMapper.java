package com.example.mapstruct.config.mapstruct;

import com.example.mapstruct.config.dto.OtherDTO;
import com.example.mapstruct.config.dto.UserDTO;
import com.example.mapstruct.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** 用户实体转换 */
@Mapper(componentModel = "spring")
public interface UserMapper {

  /**
   * 用户实体转换（基本映射）
   *
   * @param user 用户实体
   * @return 用户实体
   */
  @Mapping(source = "telephone", target = "tel")
  UserDTO map(User user);

  /**
   * 用户实体转换（多个源参数的映射方法）
   *
   * @param user 用户实体
   * @param otherDTO 其他信息DTO
   * @return 用户实体
   */
  @Mapping(source = "user.telephone", target = "tel")
  @Mapping(source = "otherDTO.email", target = "mail")
  UserDTO map(User user, OtherDTO otherDTO);
}
