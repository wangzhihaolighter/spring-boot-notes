# 06-cache：缓存支持

使用spring boot整合常用的几种缓存框架

## spring cache

官网说明：

> If you do not add any specific cache library, Spring Boot auto-configures a simple provider that uses concurrent maps in memory. When a cache is required (such as piDecimals in the preceding example), this provider creates it for you. The simple provider is not really recommended for production usage, but it is great for getting started and making sure that you understand the features.

谷歌翻译下：

>如果您不添加任何特定的缓存库，Spring Boot会自动配置一个在内存中使用并发映射的简单提供程序。当需要缓存时（例如piDecimals在前面的示例中），此提供程序会为您创建缓存。简单的提供程序并不是真正推荐用于生产用途，但它非常适合入门并确保您了解这些功能。

所以，这里不指定任何的缓存库，仅用于了解spring boot的缓存支持。

## Ehcache

ehcache是一个开源的，纯java进程内的缓存框架。它具有快速，简单，具有多种缓存策略等特点。

## Guava Cache

专门用作 JVM 缓存的开源工具

## Memcached

协议简单、基于libevent的事件处理、内置内存存储方式、memcached不互相通信的分布式。 各个memcached不会互相通信以共享信息，分布策略由客户端实现。不会对数据进行持久化，重启memcached、重启操作系统会导致全部数据消失。

Memcached常见的应用场景是存储一些读取频繁但更新较少的数据，如静态网页、系统配置及规则数据、活跃用户的基本数据和个性化定制数据、准实时统计信息等。

## redis

Redis是一个key-value存储系统。和Memcached类似，它支持存储的value类型相对更多，包括string、list、set、zset(有序集合)和hash。这些数据类型都支持push/pop、add/remove及取交集并集和差集及更丰富的操作，而且这些操作都是原子性的。在此基础上，redis支持各种不同方式的排序和算法。