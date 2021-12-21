package com.example.testing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@DisplayName("指定测试方法执行顺序策略的单元测试")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestMethodOrderTests {

  @Test
  @Order(3)
  void test3() {}

  @Test
  @Order(2)
  void test2() {}

  @Test
  @Order(1)
  void test1() {}
}
