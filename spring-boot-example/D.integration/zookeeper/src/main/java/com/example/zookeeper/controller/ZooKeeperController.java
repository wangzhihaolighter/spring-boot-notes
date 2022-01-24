package com.example.zookeeper.controller;

import com.example.zookeeper.service.ServiceDiscovery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
