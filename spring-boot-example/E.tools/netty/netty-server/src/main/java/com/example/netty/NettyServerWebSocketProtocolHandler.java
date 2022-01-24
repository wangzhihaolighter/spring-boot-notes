package com.example.netty;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** Netty Server WebSocket 消息处理 */
@Slf4j
@Sharable
@Component
public class NettyServerWebSocketProtocolHandler
    extends SimpleChannelInboundHandler<WebSocketFrame> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
    if (msg instanceof TextWebSocketFrame) {
      TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
      // 处理数据
      log.info("接收消息：{}", textWebSocketFrame.text());
      // 响应客户端
      ctx.channel().writeAndFlush(new TextWebSocketFrame("服务端接收到了你的消息 " + LocalDateTime.now()));
    } else {
      // 不接受文本以外的数据帧类型
      ctx.channel()
          .writeAndFlush(WebSocketCloseStatus.INVALID_MESSAGE_TYPE)
          .addListener(ChannelFutureListener.CLOSE);
    }
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
    log.info("channel 创建：{}", ctx.channel().remoteAddress());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    super.channelInactive(ctx);
    log.info("channel 断开：{}", ctx.channel().remoteAddress());
  }

  /** 异常处理，关闭channel */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
