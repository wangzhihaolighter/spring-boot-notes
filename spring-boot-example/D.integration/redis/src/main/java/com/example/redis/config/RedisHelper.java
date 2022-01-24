package com.example.redis.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/** redis工具类 */
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
    LettuceConnectionFactory connectionFactory =
        (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
    assert connectionFactory != null;
    connectionFactory.setDatabase(database);
    // 需要重置连接
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
   * @param key 键
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

  /* string */

  /**
   * 写入redis
   *
   * @param key 键
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
   * @param key 键
   * @param value 值
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

  /* list */

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
   * @param key 键
   * @param value 值
   * @return list中的下标
   */
  public Long addList(String key, String value) {
    return redisTemplate.boundListOps(key).rightPush(value);
  }

  /**
   * 添加一个list
   *
   * @param key 键
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
   * @param key 键
   * @param strings 值
   * @return list中的下标
   */
  public Long addList(String key, String... strings) {
    return redisTemplate.boundListOps(key).rightPushAll(strings);
  }

  /**
   * 移除list中 count个value为object的值,并且返回移除的数量
   *
   * @param key 键
   * @param object 值
   * @return 移除的数量
   */
  public Long removeListValue(String key, Object object) {
    return redisTemplate.boundListOps(key).remove(0, object);
  }

  /* set */

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
   * @param key 键
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

  /* zset */

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
   * @param key 键
   * @param start 开始索引
   * @param end 结束索引
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
   * @param key 键
   * @param start 开始索引
   * @param end 结束索引
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
   * @param key 键
   * @param start 开始索引
   * @param end 结束索引
   */
  public void removeZSetRangeByScore(String key, double start, double end) {
    redisTemplate.boundZSetOps(key).removeRangeByScore(start, end);
  }

  /**
   * 设置ZSet的过期时间,单位：秒
   *
   * @param key 键
   * @param time 时间，单位：秒
   * @return 是否成功
   */
  public Boolean setZSetExpireTime(String key, Long time) {
    return redisTemplate.boundZSetOps(key).expire(time, TimeUnit.SECONDS);
  }

  /**
   * 添加有序集合ZSET 默认按照score升序排列，存储格式K(1)==V(n)，V(1)=S(1)
   *
   * @param key 键
   * @param score 排序因子
   * @param value 值
   * @return 是否成功
   */
  public Boolean addZSet(String key, double score, String value) {
    return redisTemplate.boundZSetOps(key).add(value, score);
  }

  /* hash */

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
   * @param key 键
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
   * @param key 键
   * @param field map中的键
   * @param value map中键对应的value
   */
  public void addMap(String key, String field, Object value) {
    redisTemplate.boundHashOps(key).put(field, value);
  }

  /**
   * 删除map中的某个对象
   *
   * @param key map对应的key
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
