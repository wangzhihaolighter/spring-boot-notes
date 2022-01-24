package com.example.zookeeper.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 基于Zookeeper的服务发现
 */
@Slf4j
@Component
public class ServiceDiscovery {

    private volatile List<String> serviceAddressList = new ArrayList<>();

    private final ZooKeeper zkClient;

    public ServiceDiscovery(ZooKeeper zkClient) {
        this.zkClient = zkClient;
    }

    /**
     * 通过服务发现，获取服务提供方的地址
     *
     * @return /
     */
    public String discover() {
        String data = null;
        watchNode(zkClient);
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

    /**
     * 获取服务地址列表
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

}