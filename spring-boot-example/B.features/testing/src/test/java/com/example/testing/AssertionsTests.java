package com.example.testing;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/** Assertions的方法抛出异常意味着测试不通过 */
@DisplayName("断言类单元测试示例")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AssertionsTests {

  @Test
  @Order(1)
  @DisplayName("多条件分组断言的单元测试")
  void groupAssertionsTest() {
    int[] numbers = {0, 1, 2, 3};
    Assertions.assertAll(
        "numbers",
        () -> Assertions.assertNotNull(numbers[0]),
        () -> Assertions.assertEquals(numbers[1], 1),
        () -> Assertions.assertTrue(numbers[2] == 2),
        () -> Assertions.assertSame(numbers[3], 3));
  }

  @Test
  @Order(2)
  @DisplayName("断言指定异常抛出的单元测试")
  public void throwAssertTest() {
    Assertions.assertThrows(ArithmeticException.class, () -> System.out.println(1 % 0));
  }

  @Test
  @Order(3)
  @DisplayName("断言给定超时时间之前完成的单元测试 - 必须等待execute方法执行完成才知道是否超时")
  public void timeoutAssetTest() {
    // 如果测试方法时间超过1s将会异常
    Assertions.assertTimeout(Duration.of(1, ChronoUnit.SECONDS), () -> Thread.sleep(5000));
  }

  @Test
  @Order(4)
  @DisplayName("断言给定超时时间之前完成的测试 - 一个新的线程来执行execute方法，避免了assertTimeout的必须等待execute执行完成的弊端")
  void timeoutAssetPreemptivelyTest() {
    Assertions.assertTimeoutPreemptively(
        Duration.of(1, ChronoUnit.SECONDS), () -> Thread.sleep(5000));
  }
}
