package com.example.security.dao;

import com.example.security.entity.Permission;
import com.example.security.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * 查询用户权限信息 - 通过用户名
   *
   * @param username 用户名
   * @return 权限信息
   */
  @Query(
      "select p from User u "
          + "left join UserRole ur on u.id = ur.userId "
          + "left join RolePermission rp on rp.roleId = ur.roleId "
          + "left join Permission p on p.id = rp.permissionId "
          + "where u.username=:username")
  List<Permission> selectPermissionsByUsername(String username);
}
