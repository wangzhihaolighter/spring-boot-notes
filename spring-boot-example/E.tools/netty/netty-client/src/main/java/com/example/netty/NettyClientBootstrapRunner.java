package com.example.netty;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/** 应用启动 - Netty 服务初始化 */
@Component
public class NettyClientBootstrapRunner implements ApplicationRunner {
  private final NettyClient nettyClient;

  public NettyClientBootstrapRunner(NettyClient nettyClient) {
    this.nettyClient = nettyClient;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    nettyClient.open();
    nettyClient.sendMsg("最是人间留不住，朱颜辞镜花辞树。");
    nettyClient.sendMsg("星垂平野阔，月涌大江流");
    nettyClient.close();
  }
}
