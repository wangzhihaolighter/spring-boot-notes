package com.example.testing.junit4;

import org.junit.*;
import org.junit.runners.MethodSorters;

/**
 * junit单元测试
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Junit4Tests {
    /**
     * `@BeforeClass`注解的public void方法将会在所有测试方法执行之前执行。
     * `@BeforeClass`注解修饰的方法必须是静态方法。
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        System.out.println("@BeforeClass修饰的方法会在所有测试方法执行之前执行");
    }

    /**
     * `@AfterClass`注解的public void方法将会在所有方法执行完毕之后执行。
     * `@AfterClass`注解修饰的方法必须是静态方法。
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("@AfterClass修饰的方法会在所有方法执行完毕之后执行");
    }

    /**
     * `@After`注解的public void方法将会在每个测试方法执行之后执行一次。
     * `@After`注解修饰的方法必须不是静态方法。
     */
    @After
    public void tearDown() throws Exception {
        System.out.println("@After修饰的方法会在每个测试方法执行之后执行一次");
    }

    /**
     * 对包含测试类的类或`@Test`注解方法使用`@Ignore`注解将使被注解的类或方法不会被当做测试执行。
     */
    @Ignore("这个方法很傲娇，不想被测试")
    @Test
    public void test() {
        System.out.println("test...");
    }

    /**
     * `@Test`注解的`public void`方法将会被当做测试用例。
     * 注解参数expected:定义测试方法应该抛出的异常，如果测试方法没有抛出异常或者抛出了一个不同的异常，测试失败。
     * 注解参数timeout:如果测试运行时间长于该定义时间，测试失败（单位为毫秒）。
     */
    @Test(expected = RuntimeException.class, timeout = 1000)
    public void test2() {
        System.out.println("test...");
        throw new RuntimeException("认错了。。。");
    }

    @Test(expected = RuntimeException.class)
    public void test3() {
        System.out.println("test...");
        System.out.println("不认错。。。");
    }

    @Test(timeout = 1000)
    public void test4() throws InterruptedException {
        System.out.println("test...");
        System.out.println("我超时了。。。");
        Thread.sleep(2000);
    }

}
