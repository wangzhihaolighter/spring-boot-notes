# 缓存抽象

官方文档：[features - caching](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-caching)

## Spring Cache 概述

Spring 3.1 引入了激动人心的基于注释（annotation）的缓存（cache）技术，它本质上不是一个具体的缓存实现方案（例如EHCache 或者 OSCache），而是一个对缓存使用的抽象，通过在既有代码中添加少量它定义的各种 annotation，即能够达到缓存方法的返回对象的效果。

Spring 的缓存技术还具备相当的灵活性，不仅能够使用 SpEL（Spring Expression Language）来定义缓存的 key 和各种 condition，还提供开箱即用的缓存临时存储方案，也支持和主流的专业缓存例如 EHCache 集成。

特点总结如下：

- 通过少量的配置 annotation 注释即可使得既有代码支持缓存
- 支持开箱即用 Out-Of-The-Box，即不用安装和部署额外第三方组件即可使用缓存
- 支持 Spring Express Language，能使用对象的任何属性或者方法来定义缓存的 key 和 condition
- 支持 AspectJ，并通过其实现任何方法的缓存支持
- 支持自定义 key 和自定义缓存管理者，具有相当的灵活性和扩展性

## 几个重要概念&缓存注解

|名称  |解释  |
|---------|---------|
|Cache     |缓存接口，定义缓存操作。实现有：RedisCache、EhCacheCache、ConcurrentMapCache等         |
|CacheManager     |缓存管理器，管理各种缓存（cache）组件         |
|@EnableCaching     |开启基于注解的缓存         |
|keyGenerator     |缓存数据时key生成策略         |
|serialize     |缓存数据时value序列化策略         |
|@CacheConfig     |统一配置本类的缓存注解的属性         |
|@Caching     |设置方法的复杂缓存规则         |
|@Cacheable     |主要针对方法配置，能够根据方法的请求参数对其进行缓存         |
|@CacheEvict     |清空缓存         |
|@CachePut     |保证方法被调用，又希望结果被缓存。与@Cacheable区别在于是否每次都调用方法，常用于更新         |

@Cacheable/@CachePut/@CacheEvict 主要的参数：


|名称  |解释  |
|---------|---------|
|value     |缓存的名称，在 spring 配置文件中定义，必须指定至少一个<br>例如：<br>@Cacheable(value=”mycache”)<br>@Cacheable(value={”cache1”,”cache2”}         |
|key     |缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，<br>如果不指定，则缺省按照方法的所有参数进行组合<br>例如：<br>@Cacheable(value=”testcache”,key=”#id”)         |
|condition     |缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存/清除缓存<br>例如：<br>@Cacheable(value=”testcache”,condition=”#userName.length()>2”)         |
|unless     |否定缓存。当条件结果为TRUE时，就不会缓存。<br>@Cacheable(value=”testcache”,unless=”#userName.length()>2”)         |
|allEntries(@CacheEvict)     |是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存<br>例如：<br>@CachEvict(value=”testcache”,allEntries=true)         |
|beforeInvocation(@CacheEvict)     |是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存。<br>缺省情况下，如果方法执行抛出异常，则不会清空缓存<br>例如：<br>@CachEvict(value=”testcache”，beforeInvocation=true)       |

## SpEL上下文数据

Spring Cache提供了一些供我们使用的SpEL上下文数据，下表直接摘自Spring官方文档：

|名称  |位置  |描述  |示例  |
|---------|---------|---------|---------|
|methodName     |root对象         |当前被调用的方法名         |#root.methodname         |
|method     |root对象         |当前被调用的方法         |#root.method.name         |
|target     |root对象         |当前被调用的目标对象实例         |#root.target         |
|targetClass     |root对象         |当前被调用的目标对象的类         |#root.targetClass         |
|args     |root对象         |当前被调用的方法的参数列表         |#root.args[0]         |
|caches     |root对象         |当前方法调用使用的缓存列表         |#root.caches[0].name         |
|Argument Name     |执行上下文         |当前被调用的方法的参数，如findArtisan(Artisan artisan),可以通过`#artsian.id`获得参数         |#artsian.id         |
|result     |执行上下文         |方法执行后的返回值（仅当方法执行后的判断有效，如 unless cacheEvict的beforeInvocation=false）         |#result         |

注意：

- 当我们要使用root对象的属性作为key时我们也可以将“#root”省略，因为Spring默认使用的就是root对象的属性。 如@Cacheable(key = "targetClass + methodName +#p0")
- 使用方法参数时我们可以直接使用“#参数名”或者“#p参数index”。 如：@Cacheable(value="users", key="#id")、@Cacheable(value="users", key="#p0")
- `T(...)`可以引用类中的对象

SpEL提供了多种运算符：

|类型  |运算符  |
|---------|---------|
|关系     |<，>，<=，>=，==，!=，lt，gt，le，ge，eq，ne         |
|算术     |+，- ，* ，/，%，^         |
|逻辑     |&&，||，!，and，or，not，between，instanceof         |
|条件     |?: (ternary)，?: (elvis)         |
|正则表达式     |matches         |
|其他类型     |?.，?[…]，![…]，^[…]，$[…]         |


## 支持的缓存提供程序

- Generic
- JCache (JSR-107) (EhCache 3, Hazelcast, Infinispan, and others)
- EhCache 2.x
- Hazelcast
- Infinispan
- Couchbase
- Redis
- Caffeine
- Simple

## Spring Boot 使用 Spring Cache

pom.xml中引入依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

在配置类上使用`@EnableCaching`开启缓存配置：

```java
@EnableCaching
@Configuration
public class CacheConfig {
}
```

## 参考

- [spring-boot-features - caching](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-caching)
- [Spring Cache 介绍](https://www.cnblogs.com/rollenholt/p/4202631.html)
