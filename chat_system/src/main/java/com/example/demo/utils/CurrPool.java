package com.example.demo.utils;

import com.example.demo.controller.WebSocketController;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *统一管理当前的Session, WebSocket, User
 * @author ALbaZhang
 * @date 2022/6/13
 */
public class CurrPool {

  public static Map<Integer, WebSocketController> webSockets = new ConcurrentHashMap<>();
  public static Map<Integer, User> currUsers = new ConcurrentHashMap<>();
  public static Map<Integer, List<Object>> currSessions = new ConcurrentHashMap<>();
}
