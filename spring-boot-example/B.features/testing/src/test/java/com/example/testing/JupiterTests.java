package com.example.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.Iterator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Slf4j
@Tag("Jupiter")
@DisplayName("jupiter单元测试示例")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 根据@Order值对测试方法进行排序，默认是根据方法名
class JupiterTests {

  /*
  在断言 API 设计上，JUnit 5 进行显著地改进，并且充分利用 Java 8 的新特性，特别是 Lambda 表达式，最终提供了新的断言类: org.junit.jupiter.api.Assertions 。
  许多断言方法接受 Lambda 表达式参数，在断言消息使用 Lambda 表达式的一个优点就是它是延迟计算的，如果消息构造开销很大，这样做一定程度上可以节省时间和资源。

  常用注解说明

  @ExtendWith：这是用来取代旧版本中的RunWith注解
  @Test 被该注解修饰的就是测试方法；
  @BeforeAll 被该注解修饰的必须是静态方法，会在所有测试方法之前执行，会被子类继承，取代低版本的BeforeClass；
  @AfterAll 被该注解修饰的必须是静态方法，会在所有测试方法执行之后才被执行，会被子类继承，取代低版本的AfterClass；
  @BeforeEach 被该注解修饰的方法会在每个测试方法执行前被执行一次，会被子类继承，取代低版本的Before；
  @AfterEach 被该注解修饰的方法会在每个测试方法执行后被执行一次，会被子类继承，取代低版本的Before；
  @DisplayName 测试方法的展现名称，在测试框架中展示，支持emoji；
  @Timeout 超时时长，被修饰的方法如果超时则会导致测试不通过；
  @Disabled 不执行的测试方法；
  @Tag 设置标签，可以作用在类和方法上，可用于单元测试执行时的标签过滤
  @TestMethodOrder 指定测试方法执行顺序策略类
  @Order 指定测试方法执行顺序值
  @ParameterizedTest 参数化测试
      @ValueSource  为参数化测试指定入参来源，支持八大基础类以及String类型,Class类型
      @NullSource  表示为参数化测试提供一个null的入参
      @EnumSource  表示为参数化测试提供一个枚举入参
      @CsvFileSource 表示读取指定CSV文件内容作为参数化测试入参
      @MethodSource 表示读取指定方法的返回值作为参数化测试入参(注意方法返回需要是一个流)
      实现org.junit.jupiter.params.provider.ArgumentsProvider接口，任何外部文件都可以作为它的入参
  @RepeatedTest 重复测试，可指定重复执行次数
  @Execution 指定多线程并发执行策略
  @TestFactory 创建动态测试的测试工厂
  @Nested 能够以静态内部成员类的形式对测试用例类进行逻辑分组
   */

  @Nested
  @DisplayName("内嵌测试 - 第一层")
  class NestedTest {
    @Test
    @DisplayName("NestedTest test")
    void test() {
      System.out.println("NestedTest test");
    }

    @Nested
    @DisplayName("内嵌单元测试 - 第二层")
    class NestedTest2 {
      @Test
      @DisplayName("NestedTest2 test")
      void test() {
        System.out.println("NestedTest2 test");
      }

      @Nested
      @DisplayName("内嵌单元测试 - 第三层")
      class NestedTest3 {
        @Test
        @DisplayName("NestedTest3 test")
        void test() {
          System.out.println("NestedTest3 test");
        }
      }
    }
  }

  @Disabled
  @Order(0)
  @Test
  @DisplayName("不执行的测试方法")
  public void disabled() {
    System.out.println("disabled");
  }

  @Order(1)
  @Test
  @DisplayName("你好，jupiter")
  public void hello() {
    System.out.println("Hello Junit Jupiter");
  }

  @Order(2)
  @RepeatedTest(10) // 表示重复执行10次
  @DisplayName("重复执行的单元测试")
  public void testRepeated() {
    log.info("testRepeated");
  }

  @Order(3)
  @RepeatedTest(10)
  @Execution(ExecutionMode.SAME_THREAD)
  @DisplayName("同一个线程内顺序执行的单元测试")
  public void testExecutionSameThread() {
    log.info("testExecutionSameThread");
  }

  @Order(4)
  @RepeatedTest(10)
  @Execution(ExecutionMode.CONCURRENT)
  @DisplayName("多线程执行的单元测试")
  public void testExecutionConcurrent() {
    log.info("testExecutionConcurrent");
  }

  @Order(5)
  @TestFactory
  @DisplayName("生成动态测试的单元测试")
  Iterator<DynamicTest> dynamicTests() {
    return Arrays.asList(
            dynamicTest("第一个动态测试", () -> assertTrue(true)),
            dynamicTest("第二个动态测试", () -> assertEquals(4, 2 * 2)))
        .iterator();
  }

  @SneakyThrows
  @Order(6)
  @Test
  @Timeout(value = 2) // 默认单位：TimeUnit.SECONDS
  @DisplayName("执行超时的单元测试")
  public void timeOutTests() {
    Thread.sleep(5000L);
  }
}
