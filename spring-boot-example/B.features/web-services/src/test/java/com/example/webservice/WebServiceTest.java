package com.example.webservice;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.junit.jupiter.api.Test;

@Slf4j
class WebServiceTest {

  @Test
  public void test() throws Exception {
    // ********** webservice客户端调用测试 **********

    // 创建动态客户端
    JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
    Client client = factory.createClient("http://localhost:8090/webservice/demo?wsdl");
    HTTPConduit conduit = (HTTPConduit) client.getConduit();
    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
    // 连接超时
    httpClientPolicy.setConnectionTimeout(2000);
    // 响应超时
    httpClientPolicy.setReceiveTimeout(120000);
    // 取消块编码
    httpClientPolicy.setAllowChunking(false);
    conduit.setClient(httpClientPolicy);
    // invoke("方法名",参数1,参数2,参数3....);
    Object[] objects = client.invoke("sayHello", "webservice");
    log.info("返回数据:" + objects[0]);
  }
}
