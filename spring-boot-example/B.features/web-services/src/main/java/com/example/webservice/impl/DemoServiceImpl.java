package com.example.webservice.impl;

import com.example.webservice.DemoService;
import java.time.LocalDateTime;
import javax.jws.WebService;
import org.springframework.stereotype.Service;

@Service
@WebService(
    serviceName = "DemoService", // 与接口中指定的name一致
    targetNamespace = "https://webservice.example.com", // 与接口中的命名空间一致，一般是接口的包名倒序
    endpointInterface = "com.example.webservice.DemoService" // 接口地址
    )
public class DemoServiceImpl implements DemoService {

  @Override
  public String sayHello(String name) {
    return String.format("[%s] Hello, %s", LocalDateTime.now(), name);
  }
}
