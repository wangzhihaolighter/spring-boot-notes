package com.example.testing.junit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.util.StringUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class Junit5Tests {

    @Test
    @DisplayName("第一次测试")
    public void firstTest() {
        System.out.println("hello world");
    }

    /*
    新的断言
        在断言 API 设计上，JUnit 5 进行显著地改进，并且充分利用 Java 8 的新特性，特别是 Lambda 表达式，最终提供了新的断言类: org.junit.jupiter.api.Assertions 。
        许多断言方法接受 Lambda 表达式参数，在断言消息使用 Lambda 表达式的一个优点就是它是延迟计算的，如果消息构造开销很大，这样做一定程度上可以节省时间和资源。
     */

    @Test
    void testGroupAssertions() {
        int[] numbers = {0, 1, 2, 3, 4};
        Assertions.assertAll("numbers",
                () -> Assertions.assertEquals(numbers[1], 1),
                () -> Assertions.assertEquals(numbers[3], 3),
                () -> Assertions.assertEquals(numbers[4], 4)
        );
    }

    @Test
    @DisplayName("异常测试")
    public void exceptionTest() {
        ArithmeticException exception = Assertions.assertThrows(
                //扔出断言异常
                ArithmeticException.class, () -> System.out.println(1 % 0));

    }

    @Test
    @DisplayName("超时测试")
    public void timeoutTest() {
        //如果测试方法时间超过1s将会异常
        Assertions.assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(500));
    }

    @Test
    @DisplayName("超时方法测试")
    void test_should_complete_in_one_second() {
        Assertions.assertTimeoutPreemptively(Duration.of(1, ChronoUnit.SECONDS), () -> Thread.sleep(2000));
    }

    @Test
    @DisplayName("测试捕获的异常")
    void assertThrowsException() {
        String str = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Integer.valueOf(str);
        });
    }

    /*
    参数化测试
        @ValueSource: 为参数化测试指定入参来源，支持八大基础类以及String类型,Class类型
        @NullSource: 表示为参数化测试提供一个null的入参
        @EnumSource: 表示为参数化测试提供一个枚举入参
        @CsvFileSource：表示读取指定CSV文件内容作为参数化测试入参
        @MethodSource：表示读取指定方法的返回值作为参数化测试入参(注意方法返回需要是一个流)
        实现org.junit.jupiter.params.provider.ArgumentsProvider接口，任何外部文件都可以作为它的入参
     */

    @ParameterizedTest
    @ValueSource(strings = {"one", "two", "three"})
    @DisplayName("参数化测试1")
    public void parameterizedTest1(String string) {
        System.out.println(string);
        assertTrue(StringUtils.isNotBlank(string));
    }

    /**
     * csv文件内容，格式：学生,年龄
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/test.CSV")  //指定csv文件位置
    @DisplayName("参数化测试-csv文件")
    public void parameterizedTest2(String name, Integer age) {
        System.out.println("name:" + name + ",age:" + age);
        Assertions.assertNotNull(name);
        Assertions.assertNotNull(age);
    }

    @ParameterizedTest
    @MethodSource("method")    //指定方法名
    @DisplayName("方法来源参数")
    public void testWithExplicitLocalMethodSource(String name) {
        System.out.println(name);
        Assertions.assertNotNull(name);
    }

    static Stream<String> method() {
        return Stream.of("apple", "banana");
    }

    /*
    内嵌单元测试
        @Nested 注解，能够以静态内部成员类的形式对测试用例类进行逻辑分组
     */

    static class Nested1 {
        @BeforeEach
        void init() {
            System.out.println("Nested1_init");
        }

        @Test
        @DisplayName("Nested")
        void isInstantiatedWithNew() {
            System.out.println("最一层--内嵌单元测试");
        }

        @Nested
        @DisplayName("Nested2")
        class Nested2 {

            @BeforeEach
            void Nested2_init() {
                System.out.println("Nested2_init");
            }

            @Test
            void Nested2_test() {
                System.out.println("第二层-内嵌单元测试");
            }

            @Nested
            @DisplayName("Nested3")
            class Nested3 {

                @BeforeEach
                void Nested3_init() {
                    System.out.println("Nested3_init");
                }

                @Test
                void Nested3_test() {
                    System.out.println("第三层-内嵌单元测试");
                }
            }
        }

    }

    @RepeatedTest(10) //表示重复执行10次
    @DisplayName("重复测试")
    public void testRepeated() {
        assertTrue(1 == 1);
    }

    @TestFactory
    @DisplayName("动态测试")
    Iterator<DynamicTest> dynamicTests() {
        return Arrays.asList(
                dynamicTest("第一个动态测试", () -> assertTrue(true)),
                dynamicTest("第二个动态测试", () -> assertEquals(4, 2 * 2))
        ).iterator();
    }
}