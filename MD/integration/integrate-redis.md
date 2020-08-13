# Spring Boot整合redis

redis官网：[redis.io](https://redis.io/)

## redis介绍

Redis 是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件。 

它支持多种类型的数据结构，如 字符串（strings）， 散列（hashes）， 列表（lists）， 集合（sets）， 有序集合（sorted sets） 与范围查询， bitmaps， hyperloglogs 和 地理空间（geospatial） 索引半径查询。 

Redis 内置了 复制（replication），LUA脚本（Lua scripting）， LRU驱动事件（LRU eviction），事务（transactions） 和不同级别的 磁盘持久化（persistence）， 并通过 Redis哨兵（Sentinel）和自动 分区（Cluster）提供高可用性（high availability）。

## 整合使用redis

Maven项目引入redis依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
```

配置文件中添加redis连接配置：

```properties
# Redis_config - 由于配置了pool属性，所以需要引入commons-pool2依赖
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接超时时间（毫秒）
spring.redis.timeout=3600ms
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.lettuce.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.lettuce.pool.max-wait=-1ms
# lettuce超时
spring.redis.lettuce.shutdown-timeout=100ms
# 连接池中的最大空闲连接（默认为8，负数表示无限）
spring.redis.lettuce.pool.max-idle=8
# 连接池中的最小空闲连接（默认为0，该值只有为正数才有作用）
spring.redis.lettuce.pool.min-idle=0
```

redis配置类：

```java
@Configuration
public class RedisConfig {

    /**
     * 使用默认的工厂初始化redis操作模板
     */
    @Primary
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(factory);
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        objectMapper.activateDefaultTyping(BasicPolymorphicTypeValidator.builder().build(), ObjectMapper.DefaultTyping.NON_FINAL);
        jsonRedisSerializer.setObjectMapper(objectMapper);

        // 值采用json序列化
        template.setValueSerializer(jsonRedisSerializer);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }

}
```

redis工具类：

```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 */
@Slf4j
@Component
public class RedisHelper {

    private final StringRedisTemplate redisTemplate;

    public RedisHelper(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /*
    数据结构：string（字符串），hash（哈希），list（列表），set（集合）及zset(sorted set：有序集合)
     */

    /**
     * 切换数据库索引
     *
     * @param database 数据库索引
     */
    public void resetDataBase(int database) {
        LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        assert connectionFactory != null;
        connectionFactory.setDatabase(database);
        //需要重置连接
        connectionFactory.afterPropertiesSet();
        connectionFactory.resetConnection();
        redisTemplate.setConnectionFactory(connectionFactory);
        log.info("redis切换数据库索引至：" + database);
    }

    /**
     * 获取所有的key
     *
     * @return 所有的key
     */
    public Set<String> getAllKeys() {
        return redisTemplate.keys("*");
    }

    /**
     * 获取对应key的value
     *
     * @param key 键
     * @return 值
     */
    public Object get(final String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 设置一个key的有效时间（单位：秒）
     *
     * @param key        键
     * @param expireTime 有效时间
     * @return 是否成功
     */
    public Boolean setExpireTime(String key, Long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 判断redis中是否有对应key的value
     *
     * @param key 键
     * @return 是否存在
     */
    public Boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取key的value类型
     *
     * @param key 键
     * @return 类型
     */
    public DataType getType(String key) {
        return redisTemplate.type(key);
    }

    /**
     * 删除对应key-value
     *
     * @param key 键
     */
    public Boolean remove(final String key) {
        if (exists(key)) {
            return redisTemplate.delete(key);
        }
        return false;
    }

    /**
     * 批量删除对应key-value
     *
     * @param keys key列表
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 模糊移除 支持*号等匹配移除
     *
     * @param blear 模糊匹配的键值
     */
    public void removeBlear(String blear) {
        redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys(blear)));
    }

    /* TODO string */

    /**
     * 写入redis
     *
     * @param key   键
     * @param value 值
     * @return 写入是否成功
     */
    public boolean set(final String key, String value) {
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 写入redis，并设置有效时间（单位：秒）
     *
     * @param key        键
     * @param value      值
     * @param expireTime 有效时间,单位：秒
     * @return 写入是否成功
     */
    public boolean set(final String key, String value, Long expireTime) {
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取redis中对应key的value
     *
     * @param key 键
     * @return 值
     */
    public String getStringValue(String key) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 获取所有的key-values
     *
     * @return 键-值 列表
     */
    public Map<String, String> getAllStringValue() {
        Set<String> keys = getAllKeys();
        Map<String, String> map = new HashMap<>(keys.size());
        for (String key : keys) {
            map.put(key, getStringValue(key));
        }
        return map;
    }

    /**
     * 获取所有的普通key-value
     *
     * @return key-value map
     */
    public Map<String, String> getAllString() {
        Set<String> stringSet = getAllKeys();
        Map<String, String> map = new HashMap<>(stringSet.size());
        for (String k : stringSet) {
            if (getType(k) == DataType.STRING) {
                map.put(k, getStringValue(k));
            }
        }
        return map;
    }

    /* TODO list */

    /**
     * 获取key的list value数量
     *
     * @param key 键
     * @return 值数量
     */
    public Long getListSize(String key) {
        return redisTemplate.boundListOps(key).size();
    }

    /**
     * 获取key的value值
     *
     * @param key 键
     * @return value值列表
     */
    public List<String> getList(String key) {
        return redisTemplate.boundListOps(key).range(0, getListSize(key));
    }

    /**
     * 获取所有的List类型key-value列表
     *
     * @return List类型key-value列表
     */
    public Map<String, List<String>> getAllList() {
        Set<String> stringSet = getAllKeys();
        Map<String, List<String>> map = new HashMap<>(stringSet.size());
        for (String k : stringSet) {
            if (getType(k) == DataType.LIST) {
                map.put(k, getList(k));
            }
        }
        return map;
    }

    /**
     * 向list中增加值
     *
     * @param key   键
     * @param value 值
     * @return list中的下标
     */
    public Long addList(String key, String value) {
        return redisTemplate.boundListOps(key).rightPush(value);
    }

    /**
     * 添加一个list
     *
     * @param key       键
     * @param valueList list值
     */
    public void addList(String key, List<String> valueList) {
        for (String value : valueList) {
            addList(key, value);
        }
    }

    /**
     * 向list中增加值
     *
     * @param key     键
     * @param strings 值
     * @return list中的下标
     */
    public Long addList(String key, String... strings) {
        return redisTemplate.boundListOps(key).rightPushAll(strings);
    }

    /**
     * 移除list中 count个value为object的值,并且返回移除的数量
     *
     * @param key    键
     * @param object 值
     * @return 移除的数量
     */
    public Long removeListValue(String key, Object object) {
        return redisTemplate.boundListOps(key).remove(0, object);
    }

    /* TODO set */

    /**
     * 获得整个set
     *
     * @param key 键
     * @return set值
     */
    public Set<String> getSet(String key) {
        return redisTemplate.boundSetOps(key).members();
    }

    /**
     * 获取所有的Set key-value
     *
     * @return set key-value集合
     */
    public Map<String, Set<String>> getAllSet() {
        Set<String> stringSet = getAllKeys();
        Map<String, Set<String>> map = new HashMap<>(stringSet.size());
        for (String k : stringSet) {
            if (getType(k) == DataType.SET) {
                map.put(k, getSet(k));
            }
        }
        return map;
    }

    /**
     * 设置Set的过期时间，单位：秒
     *
     * @param key  键
     * @param time 时间，单位：秒
     * @return 是否成功
     */
    public Boolean setSetExpireTime(String key, Long time) {
        return redisTemplate.boundSetOps(key).expire(time, TimeUnit.SECONDS);
    }

    /**
     * 移除set中的某些值
     *
     * @param key 键
     * @param obj set中的值
     * @return
     */
    public Long removeSetValue(String key, Object obj) {
        return redisTemplate.boundSetOps(key).remove(obj);
    }

    /* TODO zset */

    /**
     * 获取zset对应key的集合元素个数
     *
     * @param key 键
     * @return 集合元素个数
     */
    public Long getZSetSize(String key) {
        return redisTemplate.boundZSetOps(key).size();
    }

    /**
     * 获取对应key的有序集合ZSET集合，索引start<=index<=end的元素子集，倒序
     *
     * @param key   键
     * @param start 开始索引
     * @param end   结束索引
     * @return 整个有序集合ZSET
     */
    public Set<String> getZSetReverseRange(String key, long start, long end) {
        return redisTemplate.boundZSetOps(key).reverseRange(start, end);
    }

    /**
     * 获取整个有序集合ZSET，倒序
     *
     * @param key 键
     * @return 整个有序集合ZSET
     */
    public Set<String> getZSetReverseRange(String key) {
        return getZSetReverseRange(key, 0, getZSetSize(key));
    }

    /**
     * 获取所有的ZSet倒序 key-value 不获取权重值
     *
     * @return ZSet倒序 key-value集合
     */
    public Map<String, Set<String>> getAllZSetReverseRange() {
        Set<String> stringSet = getAllKeys();
        Map<String, Set<String>> map = new HashMap<>(stringSet.size());
        for (String k : stringSet) {
            if (getType(k) == DataType.ZSET) {
                map.put(k, getZSetReverseRange(k));
            }
        }
        return map;
    }

    /**
     * 获取对应key的有序集合ZSET集合，索引start<=index<=end的元素子集，正序
     *
     * @param key   键
     * @param start 开始索引
     * @param end   结束索引
     * @return 整个有序集合ZSET
     */
    public Set<String> getZSetRange(String key, long start, long end) {
        return redisTemplate.boundZSetOps(key).range(start, end);
    }

    /**
     * 获取整个有序集合ZSET，正序
     *
     * @param key 键
     * @return 整个有序集合ZSET
     */
    public Set<String> getZSetRange(String key) {
        return getZSetRange(key, 0, getZSetSize(key));
    }

    /**
     * 获取所有的ZSet倒序 key-value 不获取权重值
     *
     * @return ZSet倒序 key-value集合
     */
    public Map<String, Set<String>> getAllZSetRange() {
        Set<String> stringSet = getAllKeys();
        Map<String, Set<String>> map = new HashMap<>(stringSet.size());
        for (String k : stringSet) {
            if (getType(k) == DataType.ZSET) {
                map.put(k, getZSetRange(k));
            }
        }
        return map;
    }

    /**
     * 通过索引删除ZSet中的值
     *
     * @param key   键
     * @param start 开始索引
     * @param end   结束索引
     */
    public void removeZSetRangeByScore(String key, double start, double end) {
        redisTemplate.boundZSetOps(key).removeRangeByScore(start, end);
    }

    /**
     * 设置ZSet的过期时间,单位：秒
     *
     * @param key  键
     * @param time 时间，单位：秒
     * @return 是否成功
     */
    public Boolean setZSetExpireTime(String key, Long time) {
        return redisTemplate.boundZSetOps(key).expire(time, TimeUnit.SECONDS);
    }

    /**
     * 添加有序集合ZSET
     * 默认按照score升序排列，存储格式K(1)==V(n)，V(1)=S(1)
     *
     * @param key   键
     * @param score 排序因子
     * @param value 值
     * @return 是否成功
     */
    public Boolean addZSet(String key, double score, String value) {
        return redisTemplate.boundZSetOps(key).add(value, score);
    }

    /* TODO hash */

    /**
     * 获取对应key的map对象
     *
     * @param key 键
     * @return map对象
     */
    public Map<Object, Object> getMap(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }

    /**
     * 获取map对象的大小
     *
     * @param key 键
     * @return map对象的大小
     */
    public Long getMapSize(String key) {
        return redisTemplate.boundHashOps(key).size();
    }

    /**
     * 判断map中对应key的key是否存在
     *
     * @param key   键
     * @param field map对应的key
     * @return 是否存在
     */
    public Boolean hasMapKey(String key, String field) {
        return redisTemplate.boundHashOps(key).hasKey(field);
    }

    /**
     * 获取对应key的map的value集合（map的value集合）
     *
     * @param key 键
     * @return map对应key的value
     */
    public List<Object> getMapFieldValue(String key) {
        return redisTemplate.boundHashOps(key).values();
    }

    /**
     * 获取所有的Map key-value
     *
     * @return 所有的Map
     */
    public Map<String, Map<Object, Object>> getAllMap() {
        Set<String> stringSet = getAllKeys();
        Map<String, Map<Object, Object>> map = new HashMap<>(stringSet.size());
        for (String k : stringSet) {
            if (getType(k) == DataType.HASH) {
                map.put(k, getMap(k));
            }
        }
        return map;
    }

    /**
     * 添加map
     *
     * @param key 键
     * @param map map值
     */
    public void addMap(String key, Map<String, Object> map) {
        redisTemplate.boundHashOps(key).putAll(map);
    }

    /**
     * 向key对应的map中添加对应key-value
     *
     * @param key   键
     * @param field map中的键
     * @param value map中键对应的value
     */
    public void addMap(String key, String field, Object value) {
        redisTemplate.boundHashOps(key).put(field, value);
    }

    /**
     * 删除map中的某个对象
     *
     * @param key   map对应的key
     * @param field map中该对象的key
     */
    public void removeMapField(String key, Object... field) {
        redisTemplate.boundHashOps(key).delete(field);
    }

    /**
     * 消息发布
     *
     * @param channel 话题通道
     * @param message 消息内容
     */
    public void convertAndSend(String channel, Object message) {
        redisTemplate.convertAndSend(channel, message);
    }

}
```

## redis实现Spring Cache

引入`spring cache`依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

添加redis缓存配置：

```java
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;

@EnableCaching
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    @Resource
    private RedisConnectionFactory factory;

    /**
     * 自定义生成redis-key
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName()).append(".");
            sb.append(method.getName()).append(".");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            System.out.println("keyGenerator=" + sb.toString());
            return sb.toString();
        };
    }

    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(Objects.requireNonNull(cacheManager()));
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        // 用于捕获从Cache中进行CRUD时的异常的回调处理器。
        return new SimpleCacheErrorHandler();
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        //缓存配置对象
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                //设置缓存的默认超时时间：30分钟
                .entryTtl(Duration.ofMinutes(30L))
                //如果是空值，不缓存
                .disableCachingNullValues()
                //设置key序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                //设置value序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory)).cacheDefaults(cacheConfiguration).build();
    }
    
}
```

Spring Cache常用注解说明：

```text
@EnableCaching：
    开启注解式缓存支持
@CacheConfig：
    提供类级别的公共缓存配置
@Cacheable：
    @Cacheable可以标记在一个方法上，也可以标记在一个类上。当标记在一个方法上时表示该方法是支持缓存的，当标记在一个类上时则表示该类所有的方法都是支持缓存的。
@CachePut：
    更新缓存(不会影响到方法的运行)
    @CachePut也可以声明一个方法支持缓存功能。与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
@CacheEvict：
    用来标注在需要清除缓存元素的方法或类上的
@Caching：
    重新组合要应用于方法的多个缓存操作
    @Caching注解可以让我们在一个方法或者类上同时指定多个Spring Cache相关的注解。其拥有三个属性：cacheable、put和evict，分别用于指定@Cacheable、@CachePut和@CacheEvict。
```

## redis的发布和订阅

## 定义消息接听对象

方式一：RedisReceiver可以是普通类，普通类的写法如下，接收的时候只接收到消息，没有频道名

```java
@Component
public class SimpleRedisReceiver {

    public void receiveMessage(String message) {
        System.out.println("Received a message：" + message);
    }

}
```

方式二：继承MessageListener，能拿到消息体和频道名

```java
@Component
public class RedisChannelMessageReceiver implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        //消息体
        System.out.println("消息体：" + new String(message.getBody()));
        //频道名
        System.out.println("频道名：" + new String(message.getChannel()));
        //模式
        System.out.println("模式：" + new String(pattern));
    }

}
```

### 配置监听适配器、消息监听容器

监听消息频道常量类

```java
/**
 * redis监听消息频道常量类
 */
public class RedisListenerChannelConstant {

    public static final String CHANNEL_SIMPLE = "channel:simple";

    public static final String CHANNEL_WELCOME = "channel:welcome";

    public static final String CHANNEL_USER = "channel:user";

}
```

redis消息监听器容器配置类

```java
@Configuration
public class RedisListenerConfig {

    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     *
     * @param connectionFactory /
     * @return /
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        //可以添加多个 messageListener
        container.addMessageListener(simpleListenerAdapter(), new PatternTopic(RedisListenerChannelConstant.CHANNEL_SIMPLE));
        List<Topic> topicList = new ArrayList<>();
        topicList.add(new PatternTopic(RedisListenerChannelConstant.CHANNEL_WELCOME));
        topicList.add(new PatternTopic(RedisListenerChannelConstant.CHANNEL_USER));
        container.addMessageListener(channelMessageListenerAdapter(), topicList);

        return container;
    }


    /*
    消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     */

    @Bean
    MessageListenerAdapter simpleListenerAdapter() {
        return new MessageListenerAdapter(new SimpleRedisReceiver(), "receiveMessage");
    }

    @Bean
    MessageListenerAdapter channelMessageListenerAdapter() {
        return new MessageListenerAdapter(new RedisChannelMessageReceiver());
    }

}
```

## RedisTemplate 执行 lua 脚本

### 方式一：lua 脚本文件

新建 redis/limit.lua 脚本文件

```lua
-- KEYS[1]代表自增key值
-- ARGV[1]代表过期时间
local c = redis.call('incr', KEYS[1])
if tonumber(c) == 1 then
    c = redis.call('expire', KEYS[1], ARGV[1])
    return c
end
return c
```

使用 RedisTemplate 执行lua脚本

```java
// 执行 lua 脚本
DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
// 指定 lua 脚本
redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/limit.lua")));
// 指定返回类型
redisScript.setResultType(Long.class);
// 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
Long result = redisTemplate.execute(redisScript, keys, args);
```

### 方式二：直接编写 lua 脚本（字符串）

```java
String RELEASE_LOCK_LUA_SCRIPT = "local c = redis.call('incr', KEYS[1])\n" +
        "if tonumber(c) == 1 then\n" +
        "    c = redis.call('expire', KEYS[1], ARGV[1])\n" +
        "    return c\n" +
        "end\n" +
        "return c";

// 指定 lua 脚本，并且指定返回值类型
DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT,Long.class);
// 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
Long result = redisTemplate.execute(redisScript, keys, args);
```
