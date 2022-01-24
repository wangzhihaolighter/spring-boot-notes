package com.example.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClientWebSocketProtocolHandler extends SimpleChannelInboundHandler<Object> {

  private final WebSocketClientHandshaker handshaker;
  private ChannelPromise handshakeFuture;

  public NettyClientWebSocketProtocolHandler(WebSocketClientHandshaker handshaker) {
    this.handshaker = handshaker;
  }

  public ChannelFuture handshakeFuture() {
    return handshakeFuture;
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) {
    this.handshakeFuture = ctx.newPromise();
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    handshaker.handshake(ctx.channel());
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    Channel ch = ctx.channel();

    if (!handshaker.isHandshakeComplete()) {
      handshaker.finishHandshake(ch, (FullHttpResponse) msg);
      handshakeFuture.setSuccess();
      return;
    }

    if (msg instanceof FullHttpResponse) {
      final FullHttpResponse response = (FullHttpResponse) msg;
      throw new IllegalStateException(
          "Unexpected FullHttpResponse (getStatus="
              + response.status()
              + ", content="
              + response.content().toString(CharsetUtil.UTF_8)
              + ')');
    }

    final WebSocketFrame frame = (WebSocketFrame) msg;
    if (frame instanceof TextWebSocketFrame) {
      TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
      log.info("TextWebSocketFrame: {}", textFrame.text());
    } else if (frame instanceof BinaryWebSocketFrame) {
      BinaryWebSocketFrame binFrame = (BinaryWebSocketFrame) frame;
      log.info("BinaryWebSocketFrame : {}", binFrame.content());
    } else if (frame instanceof PongWebSocketFrame) {
      log.info("WebSocket Client received pong");
    } else if (frame instanceof CloseWebSocketFrame) {
      log.info("receive close frame");
      ch.close();
    }
  }

  @Override
  public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause)
      throws Exception {
    cause.printStackTrace();

    if (!handshakeFuture.isDone()) {
      handshakeFuture.setFailure(cause);
    }

    ctx.close();
  }
}
