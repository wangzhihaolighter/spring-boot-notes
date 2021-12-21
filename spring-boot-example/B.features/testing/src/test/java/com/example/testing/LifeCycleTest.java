package com.example.testing;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("单元测试生命周期的测试示例")
public class LifeCycleTest {

  /** 在每个单元测试方法执行前执行一遍（只执行一次） */
  @BeforeAll
  public static void beforeAll() {
    System.out.println("BeforeAll execute ...");
  }

  /** 在每个单元测试方法执行后执行一遍（只执行一次） */
  @AfterAll
  public static void afterAll() {
    System.out.println("AfterAll execute ...");
  }

  /** 在每个单元测试方法执行前都执行一遍 */
  @BeforeEach
  public void beforeEach() {
    System.out.println("BeforeEach");
  }

  /** 在每个单元测试方法执行后都执行一遍 */
  @AfterEach
  public void afterEach() {
    System.out.println("AfterEach");
  }

  @Test
  void test1() {
    System.out.println("test1");
  }

  @Test
  void test2() {
    System.out.println("test2");
  }
}
