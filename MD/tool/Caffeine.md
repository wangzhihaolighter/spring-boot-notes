# Caffeine使用

## 资料

- Caffeine Github仓库：[ben-manes / caffeine](https://github.com/ben-manes/caffeine)
- Caffeine 中文文档：[caffeine / wiki / Home-zh-CN](https://github.com/ben-manes/caffeine/wiki/Home-zh-CN)
- Caffeine Maven仓库地址：[Home » com.github.ben-manes.caffeine » caffeine](https://mvnrepository.com/artifact/com.github.ben-manes.caffeine/caffeine)

## 介绍

> 来源：官方中文文档

Caffeine是一个基于Java8开发的提供了近乎最佳命中率的高性能的缓存库。

缓存和ConcurrentMap有点相似，但还是有所区别。最根本的区别是ConcurrentMap将会持有所有加入到缓存当中的元素，直到它们被从缓存当中手动移除。但是，Caffeine的缓存Cache 通常会被配置成自动驱逐缓存中元素，以限制其内存占用。在某些场景下，LoadingCache和AsyncLoadingCache 因为其自动加载缓存的能力将会变得非常实用。

Caffeine提供了灵活的构造器去创建一个拥有下列特性的缓存：

- 自动加载元素到缓存当中，异步加载的方式也可供选择
- 当达到最大容量的时候可以使用基于就近度和频率的算法进行基于容量的驱逐
- 将根据缓存中的元素上一次访问或者被修改的时间进行基于过期时间的驱逐
- 当向缓存中一个已经过时的元素进行访问的时候将会进行异步刷新
- key将自动被弱引用所封装
- value将自动被弱引用或者软引用所封装
- 驱逐(或移除)缓存中的元素时将会进行通知
- 写入传播到一个外部数据源当中
- 持续计算缓存的访问统计指标

为了提高集成度，扩展模块提供了JSR-107 JCache和Guava适配器。JSR-107规范了基于Java 6的API，在牺牲了功能和性能的代价下使代码更加规范。Guava的Cache是Caffeine的原型库并且Caffeine提供了适配器以供简单的迁移策略。

## 整合使用

Maven项目中引入依赖

```xml
<!-- https://mvnrepository.com/artifact/com.github.ben-manes.caffeine/caffeine -->
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
    <version>2.8.6</version>
</dependency>
```

### 缓存添加

Caffeine提供了四种缓存添加策略：手动加载，自动加载，手动异步加载和自动异步加载。

#### 手动加载

```java
Cache<Key, Graph> cache = Caffeine.newBuilder()
    .expireAfterWrite(10, TimeUnit.MINUTES)
    .maximumSize(10_000)
    .build();

// 查找一个缓存元素， 没有查找到的时候返回null
Graph graph = cache.getIfPresent(key);
// 查找缓存，如果缓存不存在则生成缓存元素,  如果无法生成则返回null
graph = cache.get(key, k -> createExpensiveGraph(key));
// 添加或者更新一个缓存元素
cache.put(key, graph);
// 移除一个缓存元素
cache.invalidate(key);
```

Cache 接口提供了显式搜索查找、更新和移除缓存元素的能力。

缓存元素可以通过调用 cache.put(key, value)方法被加入到缓存当中。如果缓存中指定的key已经存在对应的缓存元素的话，那么先前的缓存的元素将会被直接覆盖掉。因此，通过 cache.get(key, k -> value) 的方式将要缓存的元素通过原子计算的方式插入到缓存中，以避免和其他写入进行竞争。值得注意的是，当缓存的元素无法生成或者在生成的过程中抛出异常而导致生成元素失败，cache.get 也许会返回 null 。

当然，也可以使用Cache.asMap()所暴露出来的ConcurrentMap的方法对缓存进行操作。

#### 自动加载

```java
LoadingCache<Key, Graph> cache = Caffeine.newBuilder()
    .maximumSize(10_000)
    .expireAfterWrite(10, TimeUnit.MINUTES)
    .build(key -> createExpensiveGraph(key));

// 查找缓存，如果缓存不存在则生成缓存元素,  如果无法生成则返回null
Graph graph = cache.get(key);
// 批量查找缓存，如果缓存不存在则生成缓存元素
Map<Key, Graph> graphs = cache.getAll(keys);
```

一个LoadingCache是一个Cache 附加上 CacheLoader能力之后的缓存实现。

通过 getAll可以达到批量查找缓存的目的。 默认情况下，在getAll 方法中，将会对每个不存在对应缓存的key调用一次 CacheLoader.load 来生成缓存元素。 在批量检索比单个查找更有效率的场景下，你可以覆盖并开发CacheLoader.loadAll 方法来使你的缓存更有效率。

值得注意的是，你可以通过实现一个 CacheLoader.loadAll并在其中为没有在参数中请求的key也生成对应的缓存元素。打个比方，如果对应某个key生成的缓存元素与包含这个key的一组集合剩余的key所对应的元素一致，那么在loadAll中也可以同时加载剩下的key对应的元素到缓存当中。

#### 手动异步加载

```java
AsyncCache<Key, Graph> cache = Caffeine.newBuilder()
    .expireAfterWrite(10, TimeUnit.MINUTES)
    .maximumSize(10_000)
    .buildAsync();

// 查找缓存元素，如果不存在，则异步生成
CompletableFuture<Graph> graph = cache.get(key, k -> createExpensiveGraph(key));
```

一个AsyncCache 是 Cache 的一个变体，AsyncCache提供了在 Executor上生成缓存元素并返回 CompletableFuture的能力。这给出了在当前流行的响应式编程模型中利用缓存的能力。

synchronous()方法给 Cache提供了阻塞直到异步缓存生成完毕的能力。

当然，也可以使用 AsyncCache.asMap()所暴露出来的ConcurrentMap的方法对缓存进行操作。

默认的线程池实现是 ForkJoinPool.commonPool() ，当然你也可以通过覆盖并实现 Caffeine.executor(Executor)方法来自定义你的线程池选择。

#### 自动异步加载

```java
AsyncLoadingCache<Key, Graph> cache = Caffeine.newBuilder()
    .maximumSize(10_000)
    .expireAfterWrite(10, TimeUnit.MINUTES)
    // 你可以选择: 去异步的封装一段同步操作来生成缓存元素
    .buildAsync(key -> createExpensiveGraph(key));
    // 你也可以选择: 构建一个异步缓存元素操作并返回一个future
    .buildAsync((key, executor) -> createExpensiveGraphAsync(key, executor));

// 查找缓存元素，如果其不存在，将会异步进行生成
CompletableFuture<Graph> graph = cache.get(key);
// 批量查找缓存元素，如果其不存在，将会异步进行生成
CompletableFuture<Map<Key, Graph>> graphs = cache.getAll(keys);
```

一个 AsyncLoadingCache是一个 AsyncCache 加上 AsyncCacheLoader能力的实现。

在需要同步的方式去生成缓存元素的时候，CacheLoader是合适的选择。而在异步生成缓存的场景下， AsyncCacheLoader则是更合适的选择并且它会返回一个 CompletableFuture。

### Spring Boot中整合Caffeine

Caffeine配置

```java
@Configuration
public class CaffeineConfig {

    @Bean
    public Caffeine<Object, Object> caffeine() {
        /*
        Caffeine配置说明：
            initialCapacity=[integer]: 初始的缓存空间大小
            maximumSize=[long]: 缓存的最大条数
            maximumWeight=[long]: 缓存的最大权重
            expireAfterAccess=[duration]: 最后一次写入或访问后经过固定时间过期
            expireAfterWrite=[duration]: 最后一次写入后经过固定时间过期
            refreshAfterWrite=[duration]: 创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
            weakKeys: 打开key的弱引用
            weakValues：打开value的弱引用
            softValues：打开value的软引用
            recordStats：开发统计功能
         */
        return Caffeine.newBuilder()
                .initialCapacity(1000)
                .maximumSize(10000L)
                .expireAfterWrite(3600L, TimeUnit.SECONDS)
                ;
    }

}
```

Spring Cache配置

```java
@EnableCaching
@Configuration
public class CacheConfig extends CachingConfigurerSupport {
    @Resource
    private Caffeine<Object, Object> caffeine;

    @Bean
    public Cache<String, Object> cache() {
        return caffeine.build();
    }

    /**
     * 配置缓存管理器
     *
     * @return 缓存管理器
     */
    @Bean("caffeineCacheManager")
    @Override
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

}
```
