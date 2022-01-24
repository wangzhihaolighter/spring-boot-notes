package com.example.websocket.controller;

import com.example.websocket.websocket.SocketMsg;
import com.example.websocket.websocket.SocketMsgType;
import com.example.websocket.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/websocket")
public class WebSocketController {

  @GetMapping
  public List<SocketMsg> historyMsg(String room) {
    return WebSocketServer.getHistoryMsg(room);
  }

  @PostMapping
  public Boolean sendMsg(String room, String username, String msg) {
    WebSocketServer.sendInfo(room, new SocketMsg(SocketMsgType.INFO, username, msg));
    return true;
  }
}
