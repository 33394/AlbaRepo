package com.example.demo.controller;

import com.example.demo.dto.AjaxResult;
import com.example.demo.entity.SessionChat;
import com.example.demo.service.SessionChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.Session;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionChatController {

  @Autowired
  private SessionChatService sessionChatService;

  //查询某个用户拥有的会话列表
  @RequestMapping("/exist")
  public AjaxResult<?> existSession(@RequestBody Map<String, Integer> userId) {

    return sessionChatService.existSession(userId.get("userId"));

  }

  //创建一个会话
  @RequestMapping("/create")
  public AjaxResult<?> createSession(@RequestBody SessionChat sessionChat) {

    return sessionChatService.createSession(sessionChat);
  }

  @RequestMapping("/delete")
  public AjaxResult<?> deleteSession(@RequestBody Map<String, Integer> sessionId) {
    return sessionChatService.deleteSession(sessionId.get("id"));
  }

  @RequestMapping("/test")
  public AjaxResult<?> test() {
    System.out.println("端口检测到了");
    return AjaxResult.success("端口连接到了");
  }

  @RequestMapping("/unReadCountToZero")
  public AjaxResult<?> unReadCountToZero(@RequestBody Map<String, Integer> map) {
    return sessionChatService.unReadCountToZero(map.get("userId"), map.get("toUserId"));
  }
}
