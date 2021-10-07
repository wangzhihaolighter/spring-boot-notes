# Spring Boot整合zookeeper

## 资料

- ZooKeeper官网：[zookeeper.apache.org](https://zookeeper.apache.org/)

- ZooKeeper Maven
  仓库：[Home » org.apache.zookeeper » zookeeper](https://mvnrepository.com/artifact/org.apache.zookeeper/zookeeper)

## zookeeper介绍

ZooKeeper是一个分布式的，开放源码的分布式应用程序协调服务，是Google的Chubby一个开源的实现，是Hadoop和Hbase的重要组件。

它是一个为分布式应用提供一致性服务的软件，提供的功能包括：配置维护、域名服务、分布式同步、组服务等。

ZooKeeper的目标就是封装好复杂易出错的关键服务，将简单易用的接口和性能高效、功能稳定的系统提供给用户。

ZooKeeper包含一个简单的原语集，提供Java和C的接口。

ZooKeeper代码版本中，提供了分布式独享锁、选举、队列的接口，代码在$zookeeper_home\src\recipes。其中分布锁和队列有Java和C两个版本，选举只有Java版本。

简单来看，ZooKeeper = 文件系统 + 监听通知机制。

### zookeeper文件系统

Zookeeper维护一个类似文件系统的数据结构。

每个子目录项如 NameService 都被称作为 znode(目录节点)，和文件系统一样，我们能够自由的增加、删除znode，在一个znode下增加、删除子znode，唯一的不同在于znode是可以存储数据的。

有四种类型的znode：

- PERSISTENT-持久化目录节点：客户端与zookeeper断开连接后，该节点依旧存在
- PERSISTENT_SEQUENTIAL-持久化顺序编号目录节点：客户端与zookeeper断开连接后，该节点依旧存在，只是Zookeeper给该节点名称进行顺序编号
- EPHEMERAL-临时目录节点：客户端与zookeeper断开连接后，该节点被删除
- EPHEMERAL_SEQUENTIAL-临时顺序编号目录节点：客户端与zookeeper断开连接后，该节点被删除，只是Zookeeper给该节点名称进行顺序编号

### 监听通知机制

客户端注册监听它关心的目录节点，当目录节点发生变化（数据改变、被删除、子目录节点增加删除）时，zookeeper会通知客户端。

## Zookeeper工作原理

### Zookeeper的角色

Zookeeper的角色：

- 领导者（leader），负责进行投票的发起和决议，更新系统状态
- 学习者（learner），包括跟随者（follower）和观察者（observer），follower用于接受客户端请求并想客户端返回结果，在选主过程中参与投票
- Observer可以接受客户端连接，将写请求转发给leader，但observer不参加投票过程，只同步leader的状态，observer的目的是为了扩展系统，提高读取速度
- 客户端（client），请求发起方

Zookeeper的核心是原子广播，这个机制保证了各个Server之间的同步，实现这个机制的协议叫做Zab协议。

Zab协议有两种模式，它们分别是恢复模式（选主）和广播模式（同步）。当服务启动或者在领导者崩溃后，Zab就进入了恢复模式，当领导者被选举出来，且大多数Server完成了和leader的状态同步以后，恢复模式就结束了。状态同步保证了leader和Server具有相同的系统状态。

为了保证事务的顺序一致性，zookeeper采用了递增的事务id号（zxid）来标识事务。所有的提议（proposal）都在被提出的时候加上了zxid。实现中zxid是一个64位的数字，它高32位是epoch用来标识leader关系是否改变，每次一个leader被选出来，它都会有一个新的epoch，标识当前属于那个leader的统治时期。低32位用于递增计数。

每个Server在工作过程中有三种状态：

- LOOKING：当前Server不知道leader是谁，正在搜寻
- LEADING：当前Server即为选举出来的leader
- FOLLOWING：leader已经选举出来，当前Server与之同步

### Zookeeper的读写机制

- Zookeeper是一个由多个server组成的集群
- 一个leader，多个follower
- 每个server保存一份数据副本
- 全局数据一致
- 分布式读写
- 更新请求转发，由leader实施

### Zookeeper的保证

- 更新请求顺序进行，来自同一个client的更新请求按其发送顺序依次执行
- 数据更新原子性，一次数据更新要么成功，要么失败
- 全局唯一数据视图，client无论连接到哪个server，数据视图都是一致的
- 实时性，在一定事件范围内，client能读到最新数据

### Zookeeper节点数据操作流程

1. 在Client向Follower发出一个写的请求
2. Follower把请求发送给Leader
3. Leader接收到以后开始发起投票并通知Follower进行投票
4. Follower把投票结果发送给Leader
5. Leader将结果汇总后如果需要写入，则开始写入同时把写入操作通知给Leader，然后commit;
6. Follower把请求结果返回给Client

Follower主要有四个功能：

1. 向Leader发送请求（PING消息、REQUEST消息、ACK消息、REVALIDATE消息）；
2. 接收Leader消息并进行处理；
3. 接收Client的请求，如果为写请求，发送给Leader进行投票；
4. 返回Client结果。

Follower的消息循环处理如下几种来自Leader的消息：

1. PING消息： 心跳消息；
2. PROPOSAL消息：Leader发起的提案，要求Follower投票；
3. COMMIT消息：服务器端最新一次提案的信息；
4. UPTODATE消息：表明同步完成；
5. REVALIDATE消息：根据Leader的REVALIDATE结果，关闭待revalidate的session还是允许其接受消息；
6. SYNC消息：返回SYNC结果到客户端，这个消息最初由客户端发起，用来强制得到最新的更新。

## 整合使用zookeeper

Maven项目引入依赖：

```xml
<dependency>
    <groupId>org.apache.zookeeper</groupId>
    <artifactId>zookeeper</artifactId>
    <version>${zookeeper.version}</version>
</dependency>
```

其他操作zookeeper类库：[Apache Curator](http://curator.apache.org/)

### 实现一个简单的服务注册和服务发现

配置文件中填写zookeeper配置

```properties
# ----- zookeeper ----- #
# 一个免费的注册中心
zookeeper.address=106.13.122.117:2181
zookeeper.timeout=60000
```

zookeeper配置类

```java
@Component
@ConfigurationProperties("zookeeper")
public class ZooKeeperProperties {
    /**
     * 逗号分隔的ip:port，每个端口对应一个zookeeper服务器
     */
    private String address;

    /**
     * session timeout in milliseconds
     */
    private int timeout;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
```

```
@Slf4j
@Configuration
public class ZookeeperConfig {

    private final ZooKeeperProperties zooKeeperProperties;

    public ZookeeperConfig(ZooKeeperProperties zooKeeperProperties) {
        this.zooKeeperProperties = zooKeeperProperties;
    }

    @Bean(name = "zkClient")
    public ZooKeeper zkClient() {
        ZooKeeper zooKeeper = null;
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            // 连接成功后，会回调watcher监听，此连接操作是异步的，执行完new语句后，直接调用后续代码
            // connectString可指定多台服务地址 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
            zooKeeper = new ZooKeeper(zooKeeperProperties.getAddress(), zooKeeperProperties.getTimeout(), event -> {
                if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
                    //如果收到了服务端的响应事件,连接成功
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            log.info("【初始化ZooKeeper连接状态....】={}", zooKeeper.getState());

        } catch (Exception e) {
            log.error("初始化ZooKeeper连接异常....】={}", e.getMessage(), e);
        }
        return zooKeeper;
    }

}
```

基于Zookeeper的服务注册：

```java
@Slf4j
@Component
public class ServiceRegistry {

    private final ZooKeeper zkClient;

    public ServiceRegistry(ZooKeeper zkClient) {
        this.zkClient = zkClient;
    }

    /**
     * 注册服务
     *
     * @param data 服务节点
     */
    public void register(String data) {
        if (data != null) {
            createNode(zkClient, data);
        }
    }

    /**
     * 创建节点
     *
     * @param zkClient
     * @param data
     */
    private void createNode(ZooKeeper zkClient, String data) {
        try {
            //父节点不存在时进行创建
            String nodePath = "/app";
            Stat stat = zkClient.exists(nodePath, true);
            if (stat == null) {
                zkClient.create(nodePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            //这里的第一个参数和3.4.13版本的zookeeper不一样，如果不加父目录，直接就是使用/app/会报错，所以智能加父目录
            //CreateMode.EPHEMERAL_SEQUENTIAL,创建临时顺序节点,客户端会话结束后，节点将会被删除
            String childNodePath = "/serviceList";
            String createPath = zkClient.create(nodePath + childNodePath, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            log.info("create zookeeper node : {} , data : {}", createPath, data);
        } catch (KeeperException | InterruptedException e) {
            log.info("create zookeeper node error：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
```

基于Zookeeper的服务发现：

```java
@Slf4j
@Component
public class ServiceDiscovery {

    private volatile List<String> serviceAddressList = new ArrayList<>();

    public ServiceDiscovery(ZooKeeper zkClient) {
        watchNode(zkClient);
    }

    /**
     * 获取服务地址列表
     *
     * @param zkClient
     */
    private void watchNode(final ZooKeeper zkClient) {
        try {
            //获取子节点列表
            String nodePath = "/app";
            List<String> nodeList = zkClient.getChildren(nodePath, event -> {
                if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
                    // 发生子节点变化时再次调用此方法更新服务地址
                    watchNode(zkClient);
                }
            });
            List<String> dataList = new ArrayList<>();
            for (String node : nodeList) {
                byte[] bytes = zkClient.getData(nodePath + "/" + node, false, null);
                dataList.add(new String(bytes));
            }
            log.info("node data: {}", dataList);
            this.serviceAddressList = dataList;
        } catch (KeeperException | InterruptedException e) {
            log.error("", e);
            e.printStackTrace();
        }
    }

    /**
     * 通过服务发现，获取服务提供方的地址
     *
     * @return /
     */
    public String discover() {
        String data = null;
        int size = serviceAddressList.size();
        if (size > 0) {
            if (size == 1) {
                //只有一个服务提供方
                data = serviceAddressList.get(0);
                log.info("unique service address :{}", data);
            } else {
                //使用随机分配法,简单的负载均衡法
                data = serviceAddressList.get(ThreadLocalRandom.current().nextInt(size));
                log.info("choose an address : {}", data);
            }
        }
        return data;
    }

}
```

服务注册启动类，用于启动时进行服务注册

```java
@Slf4j
@Component
public class ServiceRunner implements ApplicationRunner {

    private final ZooKeeper zkClient;

    public ServiceRunner(ZooKeeper zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            //服务注册
            ServiceRegistry serviceRegistry = new ServiceRegistry(zkClient);
            String service = InetAddress.getLocalHost().getHostAddress() + ":" + 8080;
            serviceRegistry.register(service);
            log.info("服务注册：{}", service);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
```

zookeeper controller，用于获取服务列表

```java
@RestController
@RequestMapping("/zookeeper")
public class ZooKeeperController {

    private final ServiceDiscovery serviceDiscovery;

    public ZooKeeperController(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    /**
     * 服务发现
     *
     * @return 服务列表
     */
    @GetMapping("/discovery")
    public String discovery() {
        return serviceDiscovery.discover();
    }

}
```
