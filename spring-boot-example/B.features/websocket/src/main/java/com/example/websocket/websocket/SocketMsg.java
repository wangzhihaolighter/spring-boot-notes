package com.example.websocket.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

/** @author wangzhihao */
@Data
@AllArgsConstructor
public class SocketMsg {
  private SocketMsgType type;
  private String username;
  private String msg;
}
