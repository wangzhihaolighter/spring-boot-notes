package com.example.config;

import com.example.webservice.DemoService;
import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceConfig {

  private final DemoService demoService;

  public WebServiceConfig(DemoService demoService) {
    this.demoService = demoService;
  }

  /** 注入servlet bean name不能dispatcherServlet 否则会覆盖dispatcherServlet */
  @Bean(name = "cxfServlet")
  public ServletRegistrationBean<CXFServlet> cxfServlet() {
    return new ServletRegistrationBean<>(new CXFServlet(), "/webservice/*");
  }

  @Bean(name = Bus.DEFAULT_BUS_ID)
  public SpringBus springBus() {
    return new SpringBus();
  }

  /** 注册WebServiceDemoService接口到webservice服务 */
  @Bean(name = "WebServiceDemoEndpoint")
  public Endpoint webServiceDemoEndpoint() {
    EndpointImpl endpoint = new EndpointImpl(springBus(), demoService);
    endpoint.publish("/demo");
    return endpoint;
  }
}
