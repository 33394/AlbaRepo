package com.example.demo.controller;


import com.example.demo.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


@Component
@ServerEndpoint("/chat_system/{userId}/{sessionId}")
public class WebSocketController {

  private static WebSocketService webSocketService;


  //自动注入当set方法里面，类名为WebSocketServer
  @Autowired
  public void setChatService(WebSocketService webSocketService) {
    WebSocketController.webSocketService = webSocketService;
  }

  //建立连接
  @OnOpen
  public void onOpen(Session session, @PathParam(value = "userId") Integer userId, @PathParam(value = "sessionId") Integer sessionId) {

    webSocketService.onOpen(session, userId, sessionId, this);
  }

  //当会话关闭时
  @OnClose
  public void onClose(Session session) {

    webSocketService.onClose(session);
  }

  @OnMessage
  public void onMessage(String message, Session session) {


    if (message != null && !message.equals("{\"type\":0}"))
    webSocketService.onMessage(message, session);
  }

  @OnError
  public void onError(Session session, Throwable error) {

    error.printStackTrace();
  }
}
