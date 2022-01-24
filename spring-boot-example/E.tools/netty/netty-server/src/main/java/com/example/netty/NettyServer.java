package com.example.netty;

import com.example.util.SpringContextUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/** 服务启动监听器 */
@Slf4j
@Component
public class NettyServer implements ApplicationListener<ContextClosedEvent> {

  private Channel channel;

  private EventLoopGroup bossGroup;

  private EventLoopGroup workGroup;

  private final NettyServerProperties nettyServerProperties;

  public NettyServer(NettyServerProperties nettyServerProperties) {
    this.nettyServerProperties = nettyServerProperties;
  }

  public void start() {
    // 服务端口
    InetSocketAddress inetSocketAddress =
        new InetSocketAddress(nettyServerProperties.getIp(), nettyServerProperties.getPort());
    // 服务路径
    String path = nettyServerProperties.getPath();
    // 开启服务
    start(inetSocketAddress, path);
  }

  public void start(InetSocketAddress inetSocketAddress, String path) {
    // 主线程组
    bossGroup = new NioEventLoopGroup(1);
    // 工作线程组
    workGroup = new NioEventLoopGroup(200);
    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(bossGroup, workGroup);
      serverBootstrap.channel(NioServerSocketChannel.class);
      serverBootstrap.childHandler(
          new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              ChannelPipeline pipeline = socketChannel.pipeline();
              // HTTP 编解码
              pipeline.addLast(new HttpServerCodec());
              // 缓冲数据量
              pipeline.addLast(new HttpObjectAggregator(64 * 1024));
              // 支持写入大数据流
              pipeline.addLast(new ChunkedWriteHandler());
              // 校验访问路径
              pipeline.addLast(
                  new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg)
                        throws Exception {
                      if (msg instanceof FullHttpRequest) {
                        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
                        String uri = fullHttpRequest.uri();
                        if (!path.equals(uri)) {
                          ctx.channel()
                              .writeAndFlush(
                                  new DefaultFullHttpResponse(
                                      HttpVersion.HTTP_1_1,
                                      HttpResponseStatus.NOT_FOUND,
                                      Unpooled.buffer(1024)
                                          .writeBytes(
                                              "404 Not Found".getBytes(StandardCharsets.UTF_8))))
                              .addListener(ChannelFutureListener.CLOSE);
                          return;
                        }
                      }
                      super.channelRead(ctx, msg);
                    }
                  });
              // 处理 WebSocket 压缩
              pipeline.addLast(new WebSocketServerCompressionHandler());
              // 处理 WebSocket 协议与握手
              pipeline.addLast(new WebSocketServerProtocolHandler(path, null, true));
              // WebSocket 消息的自定义业务处理
              pipeline.addLast(
                  SpringContextUtils.getBean(NettyServerWebSocketProtocolHandler.class));
            }
          });

      Channel serverChannel = serverBootstrap.bind(inetSocketAddress).sync().channel();
      log.info(
          "Netty Server 服务启动：ip = {}，port = {}",
          inetSocketAddress.getHostName(),
          inetSocketAddress.getPort());
      this.channel = serverChannel;
      serverChannel.closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
      // Restore interrupted state...
      Thread.currentThread().interrupt();
    } finally {
      // 关闭主线程组
      bossGroup.shutdownGracefully();
      // 关闭工作线程组
      workGroup.shutdownGracefully();
    }
  }

  @Override
  public void onApplicationEvent(ContextClosedEvent event) {
    if (this.channel != null) {
      this.channel.close();
    }
    log.info("Netty Server 服务停止");
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
      if (bossGroup != null && !bossGroup.isShutdown() && !bossGroup.isShuttingDown()) {
        bossGroup.shutdownGracefully();
      }
      if (workGroup != null && !workGroup.isShutdown() && !workGroup.isShuttingDown()) {
        workGroup.shutdownGracefully();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      // Restore interrupted state...
      Thread.currentThread().interrupt();
    }
  }
}
