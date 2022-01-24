package com.example.zookeeper.runner;

import com.example.zookeeper.service.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 服务注册启动类
 */
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
