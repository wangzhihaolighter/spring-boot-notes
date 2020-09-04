# Spring Boot项目中的单元测试

junit4 Github 仓库地址：[junit-team / junit4](https://github.com/junit-team/junit4)

junit5 Github 仓库地址：[junit-team / junit5](https://github.com/junit-team/junit5)

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

### 单元测试顺序执行

注意，通过函数名可以实现顺序执行，执行顺序和函数的位置无关。

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
