package com.example.config.dto;

import com.example.config.validation.annotation.CannotHaveProhibitedWord;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDTO {
  /** ID */
  private Long id;
  /** 用户名 */
  @NotNull(message = "{system.validation.NotNull.username.message}")
  @Length(min = 4, max = 8, message = "{system.validation.Length.username.message}")
  private String username;
  /** 密码 */
  private String password;
  /** 性别 */
  private String sex;
  /** 邮箱 */
  @Email private String email;
  /** 手机号 */
  @Pattern(regexp = "^[\\d]{11}$", message = "{system.validation.Phone.message}")
  private String phone;
  /** 邀请码 */
  private String inviteCode;
  /** 个人简介 */
  @CannotHaveProhibitedWord private String personalProfile;
}
