package com.example.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(
    name = "DemoService", // 暴露服务名称
    targetNamespace = "https://webservice.example.com" // 命名空间,一般是接口的包名倒序
    )
public interface DemoService {

  /**
   * 你好
   *
   * @param name /
   * @return /
   */
  @WebMethod
  String sayHello(String name);
}
