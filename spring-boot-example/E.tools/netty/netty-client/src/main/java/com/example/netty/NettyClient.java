package com.example.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/** 服务启动监听器 */
@Slf4j
@Component
public class NettyClient implements ApplicationListener<ContextClosedEvent> {

  private Channel channel;

  private EventLoopGroup group;

  private final WebSocketClientProtocolConfig webSocketClientProtocolConfig;

  public NettyClient(WebSocketClientProtocolConfig webSocketClientProtocolConfig) {
    this.webSocketClientProtocolConfig = webSocketClientProtocolConfig;
  }

  public void open() {
    group = new NioEventLoopGroup();
    try {
      WebSocketClientHandshaker handshaker =
          WebSocketClientHandshakerFactory.newHandshaker(
              webSocketClientProtocolConfig.webSocketUri(),
              WebSocketVersion.V13,
              null,
              false,
              EmptyHttpHeaders.INSTANCE,
              1024 * 1024 * 10);
      NettyClientWebSocketProtocolHandler webSocketProtocolHandler =
          new NettyClientWebSocketProtocolHandler(handshaker);

      Bootstrap bootstrap = new Bootstrap();
      bootstrap
          .option(ChannelOption.SO_KEEPALIVE, true)
          .option(ChannelOption.TCP_NODELAY, true)
          .group(group)
          .channel(NioSocketChannel.class)
          .handler(new LoggingHandler(LogLevel.INFO))
          .handler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  ChannelPipeline pipeline = socketChannel.pipeline();
                  pipeline.addLast(new HttpClientCodec());
                  pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 10));
                  pipeline.addLast("ws-handler", webSocketProtocolHandler);
                }
              });

      channel =
          bootstrap
              .connect(
                  webSocketClientProtocolConfig.webSocketUri().getHost(),
                  webSocketClientProtocolConfig.webSocketUri().getPort())
              .sync()
              .channel();

      webSocketProtocolHandler.handshakeFuture().sync();

    } catch (InterruptedException e) {
      e.printStackTrace();
      // Restore interrupted state...
      Thread.currentThread().interrupt();
    }
  }

  @Override
  public void onApplicationEvent(ContextClosedEvent event) {
    close();
  }

  public void close() {
    try {
      if (channel != null
          && !channel.closeFuture().isDone()
          && !channel.closeFuture().isSuccess()) {
        channel.writeAndFlush(new CloseWebSocketFrame());
        channel.closeFuture().sync();
        log.info("Netty Client 服务停止");
      }
      if (group != null && !group.isShutdown() && !group.isShuttingDown()) {
        group.shutdownGracefully();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      // Restore interrupted state...
      Thread.currentThread().interrupt();
    }
  }

  public void sendMsg(String msg) {
    TextWebSocketFrame frame = new TextWebSocketFrame(msg);
    channel
        .writeAndFlush(frame)
        .addListener(
            (ChannelFutureListener)
                channelFuture -> {
                  if (channelFuture.isSuccess()) {
                    log.info("text send success");
                  } else {
                    log.info("text send failed  " + channelFuture.cause().getMessage());
                  }
                });
  }
}
