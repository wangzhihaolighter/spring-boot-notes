package com.example.netty;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/** 应用启动 - Netty 服务初始化 */
@Component
public class NettyServerBootstrapRunner implements ApplicationRunner {
  private final NettyServer nettyServer;

  public NettyServerBootstrapRunner(NettyServer nettyServer) {
    this.nettyServer = nettyServer;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    nettyServer.start();
  }
}
