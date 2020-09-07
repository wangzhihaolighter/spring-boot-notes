# Spring Boot项目中的单元测试

junit4 Github 仓库地址：[junit-team / junit4](https://github.com/junit-team/junit4)

junit5 Github 仓库地址：[junit-team / junit5](https://github.com/junit-team/junit5)

## Junit4使用

### Junit4常用注解

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

### Junit4单元测试执行顺序

通过`org.junit.FixMethodOrder`可以指定对应的`org.junit.runners.MethodSorters`排序策略

`MethodSorters`提供了三种策略：

- NAME_ASCENDING(MethodSorter.NAME_ASCENDING): 按照方法名字典顺序
- JVM(null): 按照JVM返回的顺序
- DEFAULT(MethodSorter.DEFAULT: 默认，通过查看`DEFAULT`这个Comparator函数源码可知，按照方法名的hashCode值排序

```java
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Junit4Test {
}
```

## Junit5使用

### 介绍

Junit5由来自三个不同子项目的几个不同模块组成。

> JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage

- JUnit Platform: Junit Platform是在JVM上启动测试框架的基础，不仅支持Junit自制的测试引擎，其他测试引擎也都可以接入。
- JUnit Jupiter: JUnit Jupiter提供了JUnit5的新的编程模型，是JUnit5新特性的核心。内部 包含了一个测试引擎，用于在Junit Platform上运行。
- JUnit Vintage: 由于JUint已经发展多年，为了照顾老的项目，JUnit Vintage提供了兼容JUnit4.x,Junit3.x的测试引擎。

### 从JUnit4迁移

- 常用注解package:`org.junit` --> `org.junit.jupiter.api`
- 断言工具package:`org.junit.Assert` --> `org.junit.jupiter.api.Assertions`
- 假设工具package:`org.junit.Assume` --> `org.junit.jupiter.api.Assumptions`
- `@Before` and `@After` no longer exist --> use `@BeforeEach` and `@AfterEach` instead.
- `@BeforeClass` and `@AfterClass` no longer exist --> use `@BeforeAll` and `@AfterAll` instead.
- `@Ignore` no longer exists --> use `@Disabled` or one of the other built-in execution conditions instead
- `@Category` no longer exists --> use `@Tag` instead.
- `@RunWith` no longer exists --> superseded by `@ExtendWith`.
- `@Rule` and `@ClassRule` no longer exist --> superseded by `@ExtendWith` and `@RegisterExtension`

### Junit5常用注解

Junit5一些常用注解说明，package位置`org.junit.jupiter.api`。

- `@Test` :表示方法是测试方法。但是与JUnit4的@Test不同，他的职责非常单一不能声明任何属性，拓展的测试将会由Jupiter提供额外测试
- `@ParameterizedTest` :表示方法是参数化测试
- `@RepeatedTest` :表示方法可重复执行
- `@TestFactory` :测试工厂进行动态测试
- `@DisplayName` :为测试类或者测试方法设置展示名称
- `@BeforeEach` :表示在每个单元测试之前执行
- `@AfterEach` :表示在每个单元测试之后执行
- `@BeforeAll` :表示在所有单元测试之前执行
- `@AfterAll` :表示在所有单元测试之后执行
- `@Tag` :表示单元测试类别，类似于JUnit4中的`@Categories`
- `@Disabled` :表示测试类或测试方法不执行，类似于JUnit4中的`@Ignore`
- `@Timeout` :表示测试方法运行如果超过了指定时间将会返回错误
- `@Nested` :嵌套嵌套测试
- `@ExtendWith` :为测试类或测试方法提供扩展类引用

### Junit5单元测试示例

```java
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
```

### Junit5单元测试执行顺序

通过`org.junit.jupiter.api.TestMethodOrder`可以指定对应的`org.junit.jupiter.api.MethodOrderer`排序策略

`MethodOrderer`提供了三种策略：

- Alphanumeric：根据测试方法的名称和形式参数列表对它们进行字母数字排序。
- OrderAnnotation：根据通过注释指定的值对测试方法进行数字排序`org.junit.jupiter.api.Order`。
- Random：伪随机地执行测试方法，并支持自定义随机策略的配置。

```java
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

//@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestMethodOrder(MethodOrderer.Random.class)
class OrderedTestsDemo {

    @Test
    @Order(1)
    void nullValues() {
        // perform assertions against null values
    }

    @Test
    @Order(2)
    void emptyValues() {
        // perform assertions against empty values
    }

    @Test
    @Order(3)
    void validValues() {
        // perform assertions against valid values
    }

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

### 事务回滚

**@Transactional**:

`@Transactional`注解修饰的类或方法，可以自动完成事务回滚。

**@Rollback**：

Spring Boot中使用`@Transactional`默认回滚事务，而使用`@Rollback`可以阻止事务自动回滚。

`@Rollback(true)`默认值是true，表示事务会自动回滚，若是想不回滚事务，将值设置为`false`即可：`@Rollback(false)`。

### 测试环境配置

**@TestConfiguration**:

`@TestConfiguration`是Spring Boot Test提供的一种工具，用它我们可以在一般的`@Configuration`之外补充测试专门用的Bean或者自定义的配置。

`@TestConfiguration`能够：

- 补充额外的Bean
- 覆盖已存在的Bean

**@ContextConfiguration**:

在 Spring Boot 测试中引入多个配置。

引入配置文件:

```java
@ContextConfiguration(Locations="classpath：applicationContext.xml")
```

引入配置类型:

```java
@ContextConfiguration(classes = SimpleConfiguration.class)
```

### 打包测试

junit提供了测试套件，就是可以运行一个测试类使得一个或一些测试类被junit测试运行。

方式：在打包测试类上使用`@RunWith`标注传递一个参数 `Suite.class` 。同时，我们还需要另外一个标注 `@Suite.SuiteClasses` ，来表明这个类是一个打包测试类。

```java
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 打包测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        Junit4Test.class,
        TestingApplicationTests.class
})
public class AllTests {
}
```

### Mock测试，在这个模拟环境中测试web端点

Mock测试使用的是`Mockito`框架，在测试的执行过程中使用Mockito的mock模拟底层数据的返回，达到测试web程序的目的。

#### 注入MockMvc实例

注入mock测试实例有两种方式。

方式一：注入 web 程序上下文，初始化MockMvc实例。

```java
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MockMvcExampleTests2 {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void exampleTest() throws Exception {
        MvcResult mvcResult = this.mvc.perform(get("/"))
                //期望响应状态正常
                .andExpect(status().isOk())
                //期望响应的信息为：Hello,World!
                .andExpect(content().string("Hello,World!"))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
```

方式二：在 Spring Boot 测试类上添加注解`@AutoConfigureMockMvc`。

```java
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcExampleTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void exampleTest() throws Exception {
        MvcResult mvcResult = this.mvc.perform(get("/"))
                //期望响应状态正常
                .andExpect(status().isOk())
                //期望响应的信息为：Hello,World!
                .andExpect(content().string("Hello,World!"))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
```

#### 使用@MockBean模拟相应对象

`@MockBean`主要作用是：使用mock对象代替原来spring的bean，然后模拟底层数据的返回，而不是调用原本真正的实现。

示例：这里接口返回的数据已经不是原本repository查询到的数据，而是mock模拟的对象返回的数据。

```java
import com.example.testing.entity.User;
import com.example.testing.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcExampleTests {

    @Autowired
    private MockMvc mvc;
    /**
     * 使用@MockBean模拟相应对象
     */
    @MockBean
    private UserRepository userRepository;

    @Test
    public void exampleTest() throws Exception {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        user.setUsername("Github");
        user.setPassword("123456");
        users.add(user);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        MvcResult mvcResult = this.mvc.perform(get("/user/query/all"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(mapper.writeValueAsString(users)))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
```

## 参考文档

- [Spring Boot Reference/boot-features-testing](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html)
- [Junit4 Home CN](https://github.com/junit-team/junit4/wiki/Home-CN)
- [Junit5 wiki](https://github.com/junit-team/junit5/wiki)
- [Junit5 新特性你用过多少？](https://cloud.tencent.com/developer/article/1513989)