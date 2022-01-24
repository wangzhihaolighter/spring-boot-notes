package com.example.websocket.websocket;

import com.example.websocket.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * web socket 服务
 *
 * @author wangzhihao
 */
@Slf4j
@ServerEndpoint("/websocket/{service}/{username}")
@Component
public class WebSocketServer {

  /** concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。 */
  private static final CopyOnWriteArraySet<WebSocketServer> WEB_SOCKET_SET =
      new CopyOnWriteArraySet<>();

  /** 消息历史记录 */
  private static final Map<String, LinkedList<SocketMsg>> WEB_SOCKET_MSG_HISTORY_MAP =
      new ConcurrentHashMap<>();

  /** 与某个客户端的连接会话，需要通过它来给客户端发送数据 */
  private Session session;

  /** 接收service */
  private String service = "";

  /** 接收username */
  private String username = "";

  /** 连接建立成功调用的方法 */
  @OnOpen
  public void onOpen(
      Session session,
      @PathParam("service") String service,
      @PathParam("username") String username) {
    this.session = session;
    // 如果存在就先删除一个，防止重复推送消息
    WEB_SOCKET_SET.removeIf(
        webSocket ->
            Objects.equals(webSocket.service, service)
                && Objects.equals(webSocket.username, username));
    WEB_SOCKET_SET.add(this);
    this.service = service;
    this.username = username;
  }

  /** 连接关闭调用的方法 */
  @OnClose
  public void onClose() {
    WEB_SOCKET_SET.remove(this);
  }

  /**
   * 收到客户端消息后调用的方法
   *
   * @param message 客户端发送过来的消息
   */
  @OnMessage
  public void onMessage(String message, Session session) {
    log.info("收到来 {} 的信息:{}", service, message);
  }

  @OnError
  public void onError(Session session, Throwable e) {
    log.error("发生错误", e);
  }

  /** 实现服务器主动推送 */
  private void sendMessage(String message) throws IOException {
    this.session.getBasicRemote().sendText(message);
  }

  /** 群发自定义消息 */
  public static void sendInfo(String service, SocketMsg socketMsg) {
    saveHistoryMsg(service, socketMsg);

    String message = JsonUtils.toJsonStr(socketMsg);
    log.info("推送消息到" + service + "，推送内容:" + message);
    WEB_SOCKET_SET.forEach(
        item -> {
          try {
            if (item.service.equals(service)) {
              item.sendMessage(message);
            }
          } catch (IOException ignored) {
          }
        });
  }

  public static synchronized LinkedList<SocketMsg> getHistoryMsg(String service) {
    return WEB_SOCKET_MSG_HISTORY_MAP.get(service);
  }

  private static synchronized void saveHistoryMsg(String service, SocketMsg socketMsg) {
    if (WEB_SOCKET_MSG_HISTORY_MAP.containsKey(service)) {
      final LinkedList<SocketMsg> list = WEB_SOCKET_MSG_HISTORY_MAP.get(service);
      list.add(socketMsg);
    } else {
      LinkedList<SocketMsg> list = new LinkedList<>();
      list.add(socketMsg);
      WEB_SOCKET_MSG_HISTORY_MAP.put(service, list);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WebSocketServer that = (WebSocketServer) o;
    return Objects.equals(session, that.session) && Objects.equals(service, that.service);
  }

  @Override
  public int hashCode() {
    return Objects.hash(session, service);
  }
}
