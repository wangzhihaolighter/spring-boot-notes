package com.example.testing;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("参数化测试的单元测试示例")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParameterizedTests {

  @Order(1)
  @ParameterizedTest
  @ValueSource(strings = {"one", "two", "three"})
  @DisplayName("指定参数值")
  public void parameterizedTest1(String string) {
    System.out.println(string);
    Assertions.assertNotNull(string);
  }

  @Order(2)
  @ParameterizedTest
  @CsvFileSource(resources = "/test.csv") // 指定csv文件位置，从test/resources目录下
  @DisplayName("从csv文件读取")
  public void parameterizedTest2(String name, Integer age) {
    System.out.println("name:" + name + ",age:" + age);
    Assertions.assertNotNull(name);
    Assertions.assertNotNull(age);
  }

  @Order(3)
  @ParameterizedTest
  @MethodSource("method")
  @DisplayName("从指定方法中获取的数据流")
  public void testWithExplicitLocalMethodSource(String name) {
    System.out.println(name);
    Assertions.assertNotNull(name);
  }

  static Stream<String> method() {
    return Stream.of("apple", "banana");
  }
}
