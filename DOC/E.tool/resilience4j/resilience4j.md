# spring-boot整合Resilience4j

## 资料

官方文档：https://resilience4j.readme.io/

官方仓库：https://github.com/resilience4j/resilience4j

官方 resilience4j 整合 spring boot 2 文档：https://resilience4j.readme.io/docs/getting-started-3

官方 resilience4j 整合 spring cloud 文档：https://resilience4j.readme.io/docs/getting-started-6

## 介绍

Resilience4j是一个轻量级、易于使用的容错库，其灵感来自Netflix Hystrix，但专为Java 8和函数式编程设计。轻量级，因为库只使用Vavr，它没有任何其他外部库依赖项。相比之下，Netflix Hystrix对Archaius有一个编译依赖关系，Archaius有更多的外部库依赖关系，如Guava和Apache Commons。

Resilience4j提供高阶函数（decorators）来增强任何功能接口、lambda表达式或方法引用，包括断路器、速率限制器、重试或舱壁。可以在任何函数接口、lambda表达式或方法引用上使用多个装饰器。优点是您可以选择所需的装饰器，而无需其他任何东西。

自从 Hystrix 停止维护之后，官方推荐大家使用 Resilience4j 来代替 Hystrix，Spring Cloud Circuit Breaker 使用 spring-cloud-starter-circuitbreaker-resilience4j。

## 核心功能

- 断路器：CircuitBreaker - 错误率过高开启断路器
- 重试器：Retry - 调用失败后发起重试
- 舱壁模式：Bulkhead - 限制并发执行的次数
  - SemaphoreBulkhead（信号量舱壁，默认），基于Java并发库中的Semaphore实现。
  - FixedThreadPoolBulkhead（固定线程池舱壁），它使用一个有界队列和一个固定线程池。
- 限流器：RateLimiter - 限制调用速率
- 超时控制器：TimeLimiter - 限制调用时长
- 缓存 - cache

## 整合

pom.xml

```xml
  <properties>
    <java.version>1.8</java.version>
    <resilience4j.version>1.7.1</resilience4j.version>
  </properties>

  <dependencies>
    <!-- web -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- aop - resilience4j依赖aop实现功能 -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>

    <!-- resilience4j -->
    <dependency>
      <groupId>io.github.resilience4j</groupId>
      <artifactId>resilience4j-spring-boot2</artifactId>
      <version>${resilience4j.version}</version>
    </dependency>

    <!-- 健康检查 - resilience4j提供了监控端口 -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
      <scope>compile</scope>
    </dependency>

    <!-- 单元测试 -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>
```

applicaition.yml

```yaml
server:
  port: 8081
spring:
  application:
    name: resilience4j-ratelimiter-demo
  profiles:
    active: resilience4j
```

application-resilience4j.yml

```yaml
############################################################
# 切面是有优先级，默认：Retry ( CircuitBreaker ( RateLimiter ( TimeLimiter ( Bulkhead ( Function ) ) ) ) )
# 可以通过 aspect-order 属性修改优先级
############################################################


# Metrics endpoint
# GET request to /actuator/metrics
# GET request to /actuator/metrics/{metric.name}.
# For example: /actuator/metrics/resilience4j.circuitbreaker.calls


# Health endpoint
management:
  health:
    circuitbreakers:
      enabled: true
    ratelimiters:
      enabled: true


# Events endpoint
# For example: /actuator/circuitbreakers
# For example: /actuator/circuitbreakerevents


# Aspect order
resilience4j:
  retry.retry-aspect-order: 2147483643
  circuitbreaker.circuit-breaker-aspect-order: 2147483644
  ratelimiter.rate-limiter-aspect-order: 2147483645
  timelimiter.time-limiter-aspect-order: 2147483646
  # bulkhead aspect order 2147483647


# 舱壁模式 - 信号量舱壁，默认
# 可配置参数见：io.github.resilience4j.common.bulkhead.configuration.BulkheadConfigurationProperties
resilience4j.bulkhead:
  configs:
    default:
      # 舱壁允许的最大并行执行量
      # 必须大于等于1
      maxConcurrentCalls: 5
      # 当试图进入一个饱和舱壁时，线程应该被阻塞的最大时间量。
      # 必须大于等于0
      maxWaitDuration: 10ms
      # 是否打印异常堆栈信息
      writableStackTraceEnabled: true
      # 事件消费者缓冲区大小
      eventConsumerBufferSize: 100
  instances:
    backendA:
      # 基础配置，使用配置组名指定
      baseConfig: default
    backendB:
      # 基础配置，使用配置组名指定
      baseConfig: default
      maxConcurrentCalls: 3


# 舱壁模式 - 固定线程池舱壁，只对CompletableFuture方法有效
# 可配置参数见：io.github.resilience4j.common.bulkhead.configuration.ThreadPoolBulkheadConfigurationProperties
resilience4j.thread-pool-bulkhead:
  configs:
    default:
      # 配置最大线程池大小
      maxThreadPoolSize: 1
      # 配置核心线程池的大小
      coreThreadPoolSize: 1
      # 配置队列的容量
      queueCapacity: 3
      # 当线程数量大于核心时，这是多余的空闲线程在终止前等待新任务的最大时间
      keepAliveDuration: 20ms
      # 是否打印异常堆栈信息
      writableStackTraceEnabled: true
      # 事件消费者缓冲区大小
      eventConsumerBufferSize: 100
  instances:
    backendC:
      # 基础配置，使用配置组名指定
      baseConfig: default


# 超时控制器
# 限制服务调用的时间，超时则返回异常或执行fallback方法
# 可配置参数见：io.github.resilience4j.common.timelimiter.configuration.TimeLimiterConfigurationProperties
resilience4j.timelimiter:
  configs:
    default:
      # 超时持续时间
      timeoutDuration: 1s
      # 是否应该在运行的future上调用cancel
      cancelRunningFuture: true
      # 事件消费者缓冲区大小
      eventConsumerBufferSize: 100
  instances:
    backendA:
      # 基础配置，使用配置组名指定
      baseConfig: default
    backendB:
      # 基础配置，使用配置组名指定
      baseConfig: default
      timeoutDuration: 3s


# 限流器
# 可配置参数见：io.github.resilience4j.common.ratelimiter.configuration.RateLimiterConfigurationProperties
resilience4j.ratelimiter:
  configs:
    default:
      # 线程等待权限的默认等待时间
      timeoutDuration: 1s
      # 限制刷新的周期。
      # 在每个周期之后，速率限制器将其权限计数返回到limitForPeriod值
      limitRefreshPeriod: 5s
      # 在一个限制刷新周期内可用的权限数
      limitForPeriod: 3
      # 开启事件订阅
      subscribeForEvents: true
      # 控制是否允许健康指示器失败
      # 当设置为 true 时，它允许健康指示器进入失败(DOWN)状态。
      # 默认情况下，速率限制器的健康指标永远不会进入不健康状态。
      allowHealthIndicatorToFail: false
      # 是否注册健康指示器
      registerHealthIndicator: true
      # 事件消费者缓冲区大小
      eventConsumerBufferSize: 100
      # 是否打印异常堆栈信息
      writableStackTraceEnabled: true
  instances:
    backendA:
      # 基础配置，使用配置组名指定
      baseConfig: default
    backendB:
      # 基础配置，使用配置组名指定
      baseConfig: default
      timeoutDuration: 0s
      limitRefreshPeriod: 5000ms
      limitForPeriod: 5


# 断路器
# 可配置参数见：io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigurationProperties
# 断路器通过一个有限状态机实现，有三种正常状态: CLOSED（关闭状态）、OPEN（开放状态）和HALF_OPEN（半开放状态），以及两种特殊状态 DISABLED（禁用状态） 和 FORCED_OPEN（强制开放状态）
# CLOSED: 关闭状态，代表正常情况下的状态，允许所有请求通过，能通过状态转换为OPEN
# HALF_OPEN: 半开状态，即允许一部分请求通过，能通过状态转换为CLOSED和OPEN
# OPEN: 熔断状态，即不允许请求通过，能通过状态转为为HALF_OPEN
# DISABLED: 禁用状态，即允许所有请求通过，出现失败率达到给定的阈值也不会熔断，不会发生状态转换
# FORCED_OPEN: 与DISABLED状态正好相反，启用CircuitBreaker，但是不允许任何请求通过，不会发生状态转换。
resilience4j.circuitbreaker:
  configs:
    # 配置组名
    default:
      # 以百分比的形式配置失败率阈值。
      # 当故障率等于或大于阈值时，断路器转换为打开并开始短路调用。
      failureRateThreshold: 50
      # 以百分比为单位配置阈值。当调用持续时间大于 slowCallDurationThreshold 时，CircuitBreaker 认为调用是慢的
      # 当慢调用的百分比等于或大于阈值时，“迂回断路器”转换为打开并开始短路调用。
      slowCallRateThreshold: 50
      # 配置高于该值的调用被视为慢调用的持续时间阈值，并提高慢调用的速率。
      slowCallDurationThreshold: 3000ms
      # 配置在迂回断路器半开时允许的调用数
      permittedNumberOfCallsInHalfOpenState: 10
      # 配置一个最大等待时间，该等待时间控制断路器在切换到开启状态之前可以保持半开启状态的最长时间。
      # 值 0 意味着断路器将在半开状态下无限等待，直到所有允许的呼叫完成。
      # spring boot 配置中必须大于等于 1
      maxWaitDurationInHalfOpenState: 1000ms
      # 配置滑动窗口的类型，该窗口用于在关闭迂回断路器时记录调用的结果。
      # 滑动窗口可以是基于计数的或基于时间的。
      # 如果滑动窗口是 COUNT_BASED，则记录并聚合最后的 slidingWindowSize 调用。
      # 如果滑动窗口是 TIME_BASED，则记录并聚合最后一次滑动窗口大小秒的调用。
      slidingWindowType: TIME_BASED
      # 配置滑动窗口的大小，该窗口用于在关闭迂回断路器时记录调用的结果。
      slidingWindowSize: 10
      # 配置需要的最小调用数量(每个滑动窗口期) ，然后 CircuitBreaker 才能计算错误率或慢调用率。
      # 例如，如果最小调用数量为10，那么在计算故障率之前，必须记录至少10个调用。
      # 如果只记录了9个调用，即使所有9个调用都失败了，该电路断路器也不会转换到打开状态。
      minimumNumberOfCalls: 10
      # 在从开启状态过渡到半开启状态之前应该等待的时间
      waitDurationInOpenState: 60000ms
      # 如果设置为 true，则意味着该 CircuitBreaker 将自动从开启状态转换为半开启状态，并且不需要调用来触发转换。
      # 一旦 waitDurationInOpenState 通过，创建一个线程来监视所有的 CircuitBreaker 实例，以将它们转换为 HALF_OPEN。
      # 然而，如果设置为 false，则只有在调用时才会转换为 HALF_OPEN，即使在传递 waitDurationInOpenState 之后也是如此。
      # 这样做的好处是没有线程监视所有断路器的状态。
      automaticTransitionFromOpenToHalfOpenEnabled: false
      # 被记录为故障并因此增加故障率的异常的列表。
      # 任何匹配或继承列表的异常都被视为失败，除非通过 ignoreExceptions 显式忽略。
      # 如果您指定了一个异常列表，那么所有其他异常都算作成功，除非 ignoreExceptions 显式地忽略它们。
      recordExceptions:
        - com.example.core.exception.BusinessRecordException
      # 一个被忽略的异常列表，这些异常既不算失败也不算成功。
      # 任何匹配或继承列表中某一个的异常都不算作失败或成功，即使这些异常是 recordExceptions 的一部分。
      ignoreExceptions:
        - com.example.core.exception.BusinessIgnoreException
      # 一个自定义 Predicate，它评估是否应将异常记录为失败。
      # 如果异常被视为失败，Predicate 必须返回 true。如果发生异常，Predicate 必须返回 false
      # 应该被视为成功，除非 ignoreExceptions 显式地忽略异常。
      recordFailurePredicate: com.example.core.resilience4j.circuitbreaker.RecordFailurePredicate
      # 是否打印异常堆栈信息
      writableStackTraceEnabled: true
      # 是否注册健康指示器
      registerHealthIndicator: true
      # 事件消费者缓冲区大小
      eventConsumerBufferSize: 100
  instances:
    backendA:
      # 基础配置，使用配置组名指定
      baseConfig: default
    backendB:
      # 基础配置，使用配置组名指定
      baseConfig: default
      slidingWindowSize: 200
      minimumNumberOfCalls: 200
    backendC:
      # 基础配置，使用配置组名指定
      baseConfig: default


# 重试器
# 可配置参数见：io.github.resilience4j.common.retry.configuration.RetryConfigurationProperties
# 注意：调整重试等待时间的策略同时只能使用一个
# 1.waitDuration
# 2.waitDuration、intervalBiFunction
# 3.waitDuration、enableExponentialBackoff
# 4.waitDuration、enableRandomizedWait
resilience4j.retry:
  configs:
    # 配置组名
    default:
      # 尝试的最大次数(包括作为第一次尝试的初始调用)
      maxAttempts: 3
      # 【重试时间间隔 - 固定等待时间】两次重试之间的固定等待时间
      waitDuration: 500ms
      # 【重试时间间隔 - 自定义间隔策略】根据尝试次数、结果或异常修改失败后等待时间间隔的函数。
      intervalBiFunction:
      # 【重试时间间隔 - 指数退避抖动】表示是否开启指数退避抖动算法，当一次调用失败后，如果在相同的时间间隔内发起重试，有可能发生连续的调用失败，因此可以开启指数退避抖动算法
      enableExponentialBackoff: false
      # 【重试时间间隔 - 指数退避抖动】表示重试时间指数退避间隔乘数
      exponentialBackoffMultiplier:
      # 【重试时间间隔 - 随机延迟】表示是否启用随机延迟策略
      enableRandomizedWait: false
      # 【重试时间间隔 - 随机延迟】随机延迟因子值（0.0 ~ 1.0）
      randomizedWaitFactor:
      # 配置一个断言判断异常是否需要重试
      retryExceptionPredicate: com.example.core.resilience4j.retry.RetryExceptionPredicate
      # 配置一个断言判断结果是否需要重试
      resultPredicate: com.example.core.resilience4j.retry.RetryResultPredicate
      # 需要重试的异常
      retry-exceptions:
        - com.example.core.exception.BusinessRecordException
      # 重试忽略的异常
      ignore-exceptions:
        - com.example.core.exception.BusinessIgnoreException
      # 事件消费者缓冲区大小
      eventConsumerBufferSize: 100
  instances:
    # 组名
    backendA:
      # 基础配置，使用配置组名指定
      baseConfig: default
    backendB:
      # 基础配置，使用配置组名指定
      baseConfig: default
    backendC:
      # 基础配置，使用配置组名指定
      baseConfig: default
    backendD:
      # 基础配置，使用配置组名指定
      baseConfig: default
    backendE:
      # 基础配置，使用配置组名指定
      baseConfig: default
      intervalBiFunction: com.example.core.resilience4j.retry.RetryIntervalBiFunction
    backendF:
      maxAttempts: 5
      waitDuration: 1000ms
      enableExponentialBackoff: true
      exponentialBackoffMultiplier: 2
      eventConsumerBufferSize: 100
    backendG:
      maxAttempts: 5
      waitDuration: 5000ms
      enableRandomizedWait: true
      randomizedWaitFactor: 0.75
      eventConsumerBufferSize: 100
```

## com.example.core

exception

```java
public class BusinessIgnoreException extends RuntimeException {

  public BusinessIgnoreException(String message) {
    super(message);
  }
}
```

```java
public class BusinessRecordException extends RuntimeException {

  public BusinessRecordException(String message) {
    super(message);
  }
}
```

```java
public class OtherRuntimeException extends RuntimeException {

  public OtherRuntimeException(String message) {
    super(message);
  }
}
```

com.example.core.resilience4j.circuitbreaker

```java
@Slf4j
public class RecordFailurePredicate implements Predicate<Throwable> {

  @Override
  public boolean test(Throwable throwable) {
    log.error("自定义断言 - 根据异常判断是否是失败记录，异常信息：{}", throwable.getMessage());
    return true;
  }
}
```

com.example.core.resilience4j.retry

```java
@Slf4j
public class RetryExceptionPredicate implements Predicate<Throwable> {

  @Override
  public boolean test(Throwable throwable) {
    log.error("根据异常判断是否需要重试，异常信息：{}", throwable.getMessage());
    return true;
  }
}
```

```java
@Slf4j
public class RetryIntervalBiFunction implements IntervalBiFunction<Object> {

  /**
   * 计算等待时间间隔
   * @param integer 尝试次数(attempt)
   * @param integers 结果或异常
   * @return 待间隔(毫秒)
   */
  @Override
  public Long apply(Integer integer, Either<Throwable, Object> integers) {
    log.info("当前重试次数：{}",integer);
    return 2000L;
  }
}
```

```java
@Slf4j
public class RetryResultPredicate implements Predicate<Object> {

  @Override
  public boolean test(Object o) {
    log.info("根据结果判断是否需要重试");
    return true;
  }
}
```

## com.example.modules.demo.service

```java
/**
 * 舱壁模式 - 信号量舱壁
 *
 * @author wangzhihao
 */
@Slf4j
@Service
public class BulkheadService {
  private final ObjectMapper mapper;

  public BulkheadService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @SneakyThrows
  @Bulkhead(name = "backendA", fallbackMethod = "fallback")
  public JsonNode backendA() {
    Thread.sleep(5000L);
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", "backendA success");
    log.info("backendA: {}", res);
    return res;
  }

  @SneakyThrows
  @Bulkhead(name = "backendB", fallbackMethod = "fallback", type = Bulkhead.Type.SEMAPHORE)
  public JsonNode backendB() {
    Thread.sleep(5000L);
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", "backendB success");
    log.info("backendB: {}", res);
    return res;
  }

  private JsonNode fallback(BulkheadFullException exception) {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", exception.getLocalizedMessage());
    log.error("fallback: {}", res);
    return res;
  }
}
```

```java
/**
 * 断路器
 *
 * @author wangzhihao
 */
@Slf4j
@Service
public class CircuitBreakerService {
  private final ObjectMapper mapper;

  public CircuitBreakerService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @CircuitBreaker(name = "backendA", fallbackMethod = "fallback")
  public JsonNode backendA() {
    throw new BusinessRecordException("backendA error");
  }

  @CircuitBreaker(name = "backendB", fallbackMethod = "fallback")
  public JsonNode backendB() {
    throw new BusinessRecordException("backendB error");
  }

  @CircuitBreaker(name = "backendC", fallbackMethod = "fallback")
  public JsonNode backendC() {
    throw new OtherRuntimeException("backendC error");
  }

  private JsonNode fallback(CallNotPermittedException exception) {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", exception.getLocalizedMessage());
    log.error("fallback: {}", res);
    return res;
  }
}
```

```java
/**
 * 限流
 *
 * @author wangzhihao
 */
@Slf4j
@Service
public class RateLimiterService {
  private final ObjectMapper mapper;

  public RateLimiterService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @RateLimiter(name = "backendA", fallbackMethod = "fallback")
  public JsonNode backendA() {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", "backendA success");
    log.info("backendA: {}", res);
    return res;
  }

  @RateLimiter(name = "backendB", fallbackMethod = "fallback")
  public JsonNode backendB() {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", "backendB success");
    log.info("backendB: {}", res);
    return res;
  }

  private JsonNode fallback(RequestNotPermitted exception) {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", exception.getLocalizedMessage());
    log.error("fallback: {}", res);
    return res;
  }
}
```

```java
/**
 * 重试
 *
 * @author wangzhihao
 */
@Slf4j
@Service
public class RetryService {
  private final ObjectMapper mapper;

  public RetryService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Retry(name = "backendA", fallbackMethod = "fallback")
  public JsonNode backendA() {
    log.error("backendA error");
    throw new BusinessRecordException("error");
  }

  @Retry(name = "backendB", fallbackMethod = "fallback")
  public JsonNode backendB() {
    log.error("backendB error");
    throw new BusinessIgnoreException("error");
  }

  @Retry(name = "backendC", fallbackMethod = "fallback")
  public JsonNode backendC() {
    log.error("backendC error");
    throw new OtherRuntimeException("error");
  }

  @Retry(name = "backendD", fallbackMethod = "fallback")
  public JsonNode backendD() {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", "success");
    log.info("backendD: {}", res);
    return res;
  }

  @Retry(name = "backendE", fallbackMethod = "fallback")
  public JsonNode backendE() {
    log.error("backendE error");
    throw new BusinessRecordException("error");
  }

  @Retry(name = "backendF", fallbackMethod = "fallback")
  public JsonNode backendF() {
    log.error("backendF error");
    throw new BusinessRecordException("error");
  }

  @Retry(name = "backendG", fallbackMethod = "fallback")
  public JsonNode backendG() {
    log.error("backendG error");
    throw new BusinessRecordException("error");
  }

  private JsonNode fallback(Throwable exception) {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", exception.getLocalizedMessage());
    log.error("fallback: {}", res);
    return res;
  }
}
```

```java
/**
 * 舱壁模式 - 固定线程池舱壁，只对CompletableFuture方法有效
 *
 * @author wangzhihao
 */
@Slf4j
@Service
public class ThreadPoolBulkheadService {
  private final ObjectMapper mapper;

  public ThreadPoolBulkheadService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @SneakyThrows
  @Bulkhead(name = "backendC", fallbackMethod = "fallback", type = Type.THREADPOOL)
  public CompletableFuture<JsonNode> backendC() {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            Thread.sleep(1000L);
          } catch (InterruptedException e) {
            log.warn("Interrupted!", e);
            Thread.currentThread().interrupt();
          }

          ObjectNode res = mapper.createObjectNode();
          res.put("msg", "backendC success");
          log.info("backendC: {}", res);
          return res;
        });
  }

  private CompletableFuture<JsonNode> fallback(BulkheadFullException exception) {
    ObjectNode res = mapper.createObjectNode();
    res.put("msg", exception.getLocalizedMessage());
    log.error("fallback: {}", res);
    return CompletableFuture.supplyAsync(() -> res);
  }
}
```

```java
/**
 * 超时
 *
 * @author wangzhihao
 */
@Slf4j
@Service
public class TimeLimiterService {
  private final ObjectMapper mapper;

  public TimeLimiterService(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @TimeLimiter(name = "backendA", fallbackMethod = "fallback")
  public CompletableFuture<JsonNode> backendA() {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            Thread.sleep(10 * 1000L);
          } catch (InterruptedException e) {
            log.warn("Interrupted!", e);
            Thread.currentThread().interrupt();
          }

          ObjectNode res = mapper.createObjectNode();
          res.put("msg", "backendA success");
          log.info("backendA: {}", res);
          return res;
        });
  }

  @SneakyThrows
  @TimeLimiter(name = "backendB", fallbackMethod = "fallback")
  public CompletableFuture<JsonNode> backendB() {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            Thread.sleep(10 * 1000L);
          } catch (InterruptedException e) {
            log.warn("Interrupted!", e);
            Thread.currentThread().interrupt();
          }

          ObjectNode res = mapper.createObjectNode();
          res.put("msg", "backendB success");
          log.info("backendB: {}", res);
          return res;
        });
  }

  private CompletableFuture<ObjectNode> fallback(TimeoutException exception) {
    return CompletableFuture.supplyAsync(
        () -> {
          ObjectNode res = mapper.createObjectNode();
          res.put("msg", exception.getLocalizedMessage());
          log.error("fallback: {}", res);
          return res;
        });
  }
}
```

### controller

```java
@RestController
@RequestMapping("/bulkhead")
public class BulkheadController {
  private final BulkheadService bulkheadService;

  public BulkheadController(BulkheadService bulkheadService) {
    this.bulkheadService = bulkheadService;
  }

  @GetMapping("/backendA")
  public ResponseEntity<Object> backendA() {
    return ResponseEntity.ok(bulkheadService.backendA());
  }

  @GetMapping("/backendB")
  public ResponseEntity<Object> backendB() {
    return ResponseEntity.ok(bulkheadService.backendB());
  }
}
```

```java
@RestController
@RequestMapping("/circuitBreaker")
public class CircuitBreakerController {
  private final CircuitBreakerService circuitBreakerService;

  public CircuitBreakerController(CircuitBreakerService circuitBreakerService) {
    this.circuitBreakerService = circuitBreakerService;
  }

  @GetMapping("/backendA")
  public ResponseEntity<Object> backendA() {
    return ResponseEntity.ok(circuitBreakerService.backendA());
  }

  @GetMapping("/backendB")
  public ResponseEntity<Object> backendB() {
    return ResponseEntity.ok(circuitBreakerService.backendB());
  }

  @GetMapping("/backendC")
  public ResponseEntity<Object> backendC() {
    return ResponseEntity.ok(circuitBreakerService.backendC());
  }
}
```

```java
@RestController
@RequestMapping("/rateLimiter")
public class RateLimiterController {
  private final RateLimiterService rateLimiterService;

  public RateLimiterController(RateLimiterService rateLimiterService) {
    this.rateLimiterService = rateLimiterService;
  }

  @GetMapping("/backendA")
  public ResponseEntity<Object> backendA() {
    return ResponseEntity.ok(rateLimiterService.backendA());
  }

  @GetMapping("/backendB")
  public ResponseEntity<Object> backendB() {
    return ResponseEntity.ok(rateLimiterService.backendB());
  }
}
```

```java
@RestController
@RequestMapping("/retry")
public class RetryController {
  private final RetryService retryService;

  public RetryController(RetryService retryService) {
    this.retryService = retryService;
  }

  @GetMapping("/backendA")
  public ResponseEntity<Object> backendA() {
    return ResponseEntity.ok(retryService.backendA());
  }

  @GetMapping("/backendB")
  public ResponseEntity<Object> backendB() {
    return ResponseEntity.ok(retryService.backendB());
  }

  @GetMapping("/backendC")
  public ResponseEntity<Object> backendC() {
    return ResponseEntity.ok(retryService.backendC());
  }

  @GetMapping("/backendD")
  public ResponseEntity<Object> backendD() {
    return ResponseEntity.ok(retryService.backendD());
  }

  @GetMapping("/backendE")
  public ResponseEntity<Object> backendE() {
    return ResponseEntity.ok(retryService.backendE());
  }

  @GetMapping("/backendF")
  public ResponseEntity<Object> backendF() {
    return ResponseEntity.ok(retryService.backendF());
  }

  @GetMapping("/backendG")
  public ResponseEntity<Object> backendG() {
    return ResponseEntity.ok(retryService.backendG());
  }
}
```

```java
@RestController
@RequestMapping("/threadPoolBulkhead")
public class ThreadPoolBulkheadController {
  private final ThreadPoolBulkheadService threadPoolBulkheadService;

  public ThreadPoolBulkheadController(ThreadPoolBulkheadService threadPoolBulkheadService) {
    this.threadPoolBulkheadService = threadPoolBulkheadService;
  }

  @SneakyThrows
  @GetMapping("/backendC")
  public ResponseEntity<Object> backendC() {
    return ResponseEntity.ok(threadPoolBulkheadService.backendC());
  }
}
```

```java
@RestController
@RequestMapping("/timeLimiter")
public class TimeLimiterController {
  private final TimeLimiterService timeLimiterService;

  public TimeLimiterController(TimeLimiterService timeLimiterService) {
    this.timeLimiterService = timeLimiterService;
  }

  @GetMapping("/backendA")
  public ResponseEntity<Object> backendA() {
    return ResponseEntity.ok(timeLimiterService.backendA());
  }

  @GetMapping("/backendB")
  public ResponseEntity<Object> backendB() {
    return ResponseEntity.ok(timeLimiterService.backendB());
  }
}
```

## 单元测试

resources/junit-platform.properties

```properties
# 并行开关true/false
junit.jupiter.execution.parallel.enabled = true
# 方法级多线程开关 same_thread/concurrent
junit.jupiter.execution.parallel.mode.default = concurrent
# 类级多线程开关 same_thread/concurrent
junit.jupiter.execution.parallel.mode.classes.default = concurrent

# 并发策略有以下三种可选：
# fixed：固定线程数，此时还要通过junit.jupiter.execution.parallel.config.fixed.parallelism指定线程数
# dynamic：表示根据处理器和核数计算线程数
# custom：自定义并发策略，通过这个配置来指定：junit.jupiter.execution.parallel.config.custom.class
junit.jupiter.execution.parallel.config.strategy = dynamic

# 并发线程数，该配置项只有当并发策略为fixed的时候才有用
junit.jupiter.execution.parallel.config.fixed.parallelism = 5
```

com.example.resilience4j

```java
@Slf4j
@DisplayName("舱壁模式 - 信号量舱壁")
class BulkheadTest extends Resilience4JApplicationTests {
  @Autowired BulkheadService bulkheadService;

  @RepeatedTest(10) // 表示重复执行10次
  void testBackendA() {
    bulkheadService.backendA();
  }

  @RepeatedTest(10) // 表示重复执行10次
  void testBackendB() {
    bulkheadService.backendB();
  }
}
```

```java
@Slf4j
@DisplayName("断路器")
class CircuitBreakerTest extends Resilience4JApplicationTests {
  @Autowired CircuitBreakerService circuitBreakerService;

  @RepeatedTest(100) // 表示重复执行100次
  void testBackendA() {
    circuitBreakerService.backendA();
  }

  @RepeatedTest(100) // 表示重复执行100次
  void testBackendB() {
    circuitBreakerService.backendB();
  }

  @RepeatedTest(100) // 表示重复执行100次
  void testBackendC() {
    circuitBreakerService.backendC();
  }
}
```

```java
@Slf4j
@DisplayName("限流")
class RateLimiterTest extends Resilience4JApplicationTests {
  @Autowired RateLimiterService rateLimiterService;

  @RepeatedTest(10) // 表示重复执行10次
  void testBackendA() {
    rateLimiterService.backendA();
  }

  @RepeatedTest(10) // 表示重复执行10次
  void testBackendB() {
    rateLimiterService.backendB();
  }
}
```

```java
@Slf4j
@DisplayName("重试器")
class RetryTest extends Resilience4JApplicationTests {
  @Autowired RetryService retryService;

  @Test
  void testBackendA() {
    retryService.backendA();
  }

  @Test
  @DisplayName("忽略指定异常，不重试")
  void testBackendB() {
    retryService.backendB();
  }

  @Test
  @DisplayName("根据异常类型判断是否重试")
  void testBackendC() {
    retryService.backendC();
  }

  @Test
  @DisplayName("根据结果判断是否需要重试")
  void backendD() {
    retryService.backendD();
  }

  @Test
  @DisplayName("重试时间间隔 - 自定义间隔策略")
  void backendE() {
    retryService.backendE();
  }

  @Test
  @DisplayName("重试时间间隔 - 指数退避抖动")
  void backendF() {
    retryService.backendF();
  }

  @Test
  @DisplayName("重试时间间隔 - 随机延迟")
  void backendG() {
    retryService.backendG();
  }
}
```

```java
@Slf4j
@DisplayName("舱壁模式 - 固定线程池舱壁")
class ThreadPoolBulkheadTest extends Resilience4JApplicationTests {
  @Autowired ThreadPoolBulkheadService threadPoolBulkheadService;

  @RepeatedTest(10) // 表示重复执行10次
  void testBackendC() throws ExecutionException, InterruptedException {
    threadPoolBulkheadService.backendC();
  }
}
```

```java
@Slf4j
@DisplayName("超时控制器")
class TimeLimiterTest extends Resilience4JApplicationTests {
  @Autowired TimeLimiterService timeLimiterService;

  @Test
  void testBackendA() throws ExecutionException, InterruptedException {
    CompletableFuture<JsonNode> future = timeLimiterService.backendA();
    future.get();
  }

  @Test
  void testBackendB() throws ExecutionException, InterruptedException {
    CompletableFuture<JsonNode> future = timeLimiterService.backendB();
    future.get();
  }
}
```

## 整合Prometheus

整合Prometheus实现容错指标监控

pom.xml

```xml
  <properties>
    <java.version>1.8</java.version>
    <resilience4j.version>1.7.1</resilience4j.version>
  </properties>

  <dependencies>
    <!-- web -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- aop - resilience4j依赖aop实现功能 -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>

    <!-- resilience4j -->
    <dependency>
      <groupId>io.github.resilience4j</groupId>
      <artifactId>resilience4j-spring-boot2</artifactId>
      <version>${resilience4j.version}</version>
    </dependency>

    <!-- 健康检查 - resilience4j提供了监控端口 -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <!-- Prometheus -->
    <dependency>
      <groupId>io.micrometer</groupId>
      <artifactId>micrometer-registry-prometheus</artifactId>
      <scope>runtime</scope>
    </dependency>

    <!-- Prometheus PushGateway -->
    <dependency>
      <groupId>io.prometheus</groupId>
      <artifactId>simpleclient_pushgateway</artifactId>
      <scope>runtime</scope>
    </dependency>

    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
      <scope>compile</scope>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>
```

application.yml

```yaml
server:
  port: 8099
spring:
  application:
    name: resilience4j-prometheus-demo
  profiles:
    active: actuator,log,resilience4j,prometheus
```

application-log.yml

```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

application-prometheus.yml

```yaml
management:
  metrics:
    tags:
      application: ${spring.application.name} # 暴露的数据中添加application label
    export:
      prometheus:
        pushgateway:
          enabled: true
          base-url: http://127.0.0.1:9091
          job: ${spring.application.name}
          push-rate: 15s
```

application-resilience4j.yml相同

### Grafana导入监控大盘

Resilience4j Grafana dashboard：https://github.com/resilience4j/resilience4j/blob/master/grafana_dashboard.json
