package com.example.dubbo.provider.service;

import com.example.dubbo.common.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

@DubboService(version = "1.0.0")
public class DemoServiceImpl implements DemoService {

  /** The default value of ${dubbo.application.name} is ${spring.application.name} */
  @Value("${dubbo.application.name}")
  private String serviceName;

  @Override
  public String sayHello(String name) {
    return String.format("[%s] : Hello, %s", serviceName, name);
  }
}
