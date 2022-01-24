package com.example.zookeeper.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

/**
 * 基于Zookeeper的服务注册
 */
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