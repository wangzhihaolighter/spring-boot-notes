# Spring Boot项目中的单元测试

## Junit4使用

### 常用注解

**@BeforeClass**:

`@BeforeClass`注解的public void方法将会在所有测试方法执行之前执行。
`@BeforeClass`注解修饰的方法必须是静态方法。

```java
@BeforeClass
public static void setUpBeforeClass() {
    System.out.println("@BeforeClass修饰的方法会在所有测试方法执行之前执行");
}
```

**@AfterClass**:

`@AfterClass`注解的public void方法将会在所有方法执行完毕之后执行。
`@AfterClass`注解修饰的方法必须是静态方法。

```java
@AfterClass
public static void tearDownAfterClass() throws Exception {
    System.out.println("@AfterClass修饰的方法会在所有方法执行完毕之后执行");
}
```

**@After**:

`@After`注解的public void方法将会在每个测试方法执行之后执行一次。
`@After`注解修饰的方法必须不是静态方法。

```java
@After
public void tearDown() throws Exception {
    System.out.println("@After修饰的方法会在每个测试方法执行之后执行一次");
}
```

**@Ignore**:

对包含测试类的类或`@Test`注解方法使用`@Ignore`注解将使被注解的类或方法不会被当做测试执行。

```java
@Ignore("这个方法很傲娇，不想被测试")
@Test
public void test() {
    System.out.println("test...");
}
```

**@Test**:

`@Test`注解的`public void`方法将会被当做测试用例。
注解参数expected:定义测试方法应该抛出的异常，如果测试方法没有抛出异常或者抛出了一个不同的异常，测试失败。
注解参数timeout:如果测试运行时间长于该定义时间，测试失败（单位为毫秒）。

```java
@Test(expected = RuntimeException.class, timeout = 1000)
public void test2() {
    System.out.println("test...");
    throw new RuntimeException("我错了。。。");
}

@Test(expected = RuntimeException.class)
public void test3() {
    System.out.println("test...");
}

@Test(timeout = 1000)
public void test4() throws InterruptedException {
    System.out.println("test...");
    Thread.sleep(2000);
}
```

## Spring Boot中使用单元测试

### 引入测试依赖

Junit的测试有局限性，无法直接测试启动后Spring环境的功能，借助Spring提供的`spring-boot-starter-test`依赖，可以便捷的调试Spring容器中Bean的功能。

```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
```

### 创建一个标准的Spring Boot的测试单元

`@SpringBootTest`这个注解所在的文件一定要和注解`@SpringBootApplication`在同个层级的文件目录下。

```xml
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

}
```

### 测试程序功能

**使用Junit进行单元测试**



## 参考文档

- [Junit Home CN](https://github.com/junit-team/junit4/wiki/Home-CN)
- [Spring Boot Reference/boot-features-testing](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html)





