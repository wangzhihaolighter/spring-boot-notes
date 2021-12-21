package com.example.testing;

import java.util.Objects;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/** Assumptions的方法抛出异常意味着测试被跳过 */
@DisplayName("假设类单元测试示例")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssumptionsTests {

  @Test
  @Order(1)
  void assumeTrueTest() {
    Assumptions.assumeTrue(Objects.equals("xxx", "yyy"), () -> "假设失败，跳过");
    System.out.println("remainder of test");
  }

  @Test
  @Order(2)
  void assumeFalseTest() {
    Assumptions.assumeFalse(Objects.equals("xxx", "xxx"), () -> "假设失败，跳过");
    System.out.println("remainder of test");
  }

  @Test
  @Order(3)
  void assumingThatTest() {
    Assumptions.assumingThat(Objects.equals("xxx", "xxx"), () -> System.out.println("假设成功"));
    Assumptions.assumingThat(Objects.equals("xxx", "yyy"), () -> System.out.println("假设失败"));
    System.out.println("remainder of test");
  }
}
