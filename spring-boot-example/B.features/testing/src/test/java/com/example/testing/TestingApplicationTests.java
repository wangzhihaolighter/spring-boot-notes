package com.example.testing;

import com.example.testing.controller.UserController;
import com.example.testing.entity.User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("系统业务测试")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class TestingApplicationTests {

  @Autowired private UserController userController;

  @Rollback(false)
  @Test
  @DisplayName("1.事务不回滚的单元测试")
  public void rollbackFalseTest() {
    User user = new User();
    user.setUsername("Github");
    user.setPassword("123456");
    Long id = userController.insert(user);
    System.out.println(id);
  }

  @Test
  @DisplayName("2.事务回滚的单元测试") // 默认回滚（需使用事务管理）
  public void rollbackTest() {
    User user = new User();
    user.setUsername("Gitlab");
    user.setPassword("123456");
    Long id = userController.insert(user);
    System.out.println(id);
  }

  @Test
  @DisplayName("3.查询结果测试")
  public void queryTest() {
    List<User> users = userController.queryAll();
    System.out.println(users);
  }
}
