package com.example.demo.service;

import com.example.demo.controller.WebSocketController;

import javax.websocket.Session;


public interface WebSocketService {

  void onOpen(Session session, Integer userId, Integer sessionId, WebSocketController webSocketController);

  void onClose(Session session);

  void onMessage(String message, Session session);

  void onError();

  void sendMessage(Integer toId, Object message);

}
