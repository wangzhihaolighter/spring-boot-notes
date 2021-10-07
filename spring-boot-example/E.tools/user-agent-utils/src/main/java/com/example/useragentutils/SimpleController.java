package com.example.useragentutils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SimpleController {

  @GetMapping("/")
  public UserAgent parseUserAgent(HttpServletRequest request) {
    UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    Browser browser = userAgent.getBrowser();
    String browserName = browser.getName();
    String group = browser.getGroup().getName();
    Version browserVersion = userAgent.getBrowserVersion();
    String version = browserVersion.getMajorVersion();
    System.out.println("浏览器名称：" + browserName);
    System.out.println("浏览器大类：" + group);
    System.out.println("详细版本：" + browserVersion);
    System.out.println("浏览器主版本：" + version);
    System.out.println("访问设备系统：" + userAgent.getOperatingSystem());
    System.out.println("访问设备类型：" + userAgent.getOperatingSystem().getDeviceType());
    System.out.println("访问设备制造厂商：" + userAgent.getOperatingSystem().getManufacturer());
    return userAgent;
  }
}
