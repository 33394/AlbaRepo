package com.example.demo.service.impl;

import com.example.demo.controller.WebSocketController;
import com.example.demo.dao.GroupDao;
import com.example.demo.dao.MessageDao;
import com.example.demo.dao.SessionChatDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.Message;
import com.example.demo.entity.SessionChat;
import com.example.demo.entity.User;
import com.example.demo.service.WebSocketService;
import com.example.demo.utils.CurrPool;
import com.example.demo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class WebSocketServiceImpl implements WebSocketService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private MessageDao messageDao;

  @Autowired
  private SessionChatDao sessionChatDao;

  @Override
  public void onOpen(Session session, Integer userId, Integer sessionId, WebSocketController webSocketController) {
    List<Object> sessionInfo = new ArrayList<>();
    sessionInfo.add(sessionId);
    sessionInfo.add(session);
    CurrPool.webSockets.put(userId, webSocketController);
    CurrPool.currSessions.put(userId, sessionInfo);
  }

  @Override
  public void onClose(Session session) {
    Integer userId = Integer.parseInt(session.getRequestParameterMap().get("userId").get(0));
    CurrPool.webSockets.remove(userId);
    CurrPool.currSessions.remove(userId);
    User user = userDao.selectByPrimaryKey(userId);
    CurrPool.currUsers.remove(user.getId());
  }

  @Override
  public void onMessage(String message, Session session) {
    String sessionId = session.getRequestParameterMap().get("sessionId").get(0);
    if (sessionId == null) {
      System.out.println("sessionId 错误");
    } else {
      //将文本消息持久化
      SessionChat sessionChat = sessionChatDao.selectByPrimaryKey(Integer.parseInt(sessionId));
      User fromUser = userDao.selectByPrimaryKey(sessionChat.getUserId());
      User toUser = userDao.selectByPrimaryKey(sessionChat.getToUserId());
      Message saveMessage = new Message();
      //创建这个文本类型的消息类型
      saveMessage.setFromId(fromUser.getId());
      saveMessage.setFromName(fromUser.getName());
      saveMessage.setToId(toUser.getId());
      saveMessage.setToName(toUser.getName());
      saveMessage.setContent(message);
      saveMessage.setGroupOrFriend(sessionChat.getSessionType());
      saveMessage.setReadStatus(0);
      saveMessage.setCreateTime(new Date());
      saveMessage.setType(1);
      //将该信息持久化
      messageDao.insertSelective(saveMessage);
      List<Object> list = CurrPool.currSessions.get(sessionChat.getToUserId());
      //如果用户不存在或者未在线
      if (list == null || list.isEmpty()) {
        //添加未读消息
        sessionChatDao.addUnReadCount(sessionChat.getToUserId(), sessionChat.getUserId());
      } else {
        //用户存在判断会话是否存在，不存在就创建一个新的会话
        SessionChat toUserChat = sessionChatDao.selectByUserIdAndToUserId(sessionChat.getToUserId(), sessionChat.getUserId());
        if (toUserChat != null) {
          //如果当前会话存在且正在发消息直接发送消息
          if (toUserChat.getId().equals(list.get(0)))
            sendTextMessage(toUserChat.getUserId(), JsonUtils.objectToJson(saveMessage));
          else {//当前会话不是该对象
            sessionChatDao.addUnReadCount(toUserChat.getUserId(), fromUser.getId());
          }
        } else {//如果当前会话不存在则创建一个
          SessionChat createSessionChat = new SessionChat();
          sessionChat.setUserId(sessionChat.getToUserId());
          sessionChat.setToUserId(sessionChat.getUserId());
          sessionChat.setSessionName(fromUser.getName() + "和" + toUser.getName() + "的聊天");
          sessionChat.setUnReadCount(1);
          sessionChatDao.insertSelective(createSessionChat);
        }
      }
    }
  }

  @Override
  public void onError() {
    System.err.println("出错了");
  }

  @Override
  public void sendMessage(Integer toId, Object message) {

    String sendMessage = JsonUtils.objectToJson(message);
    sendTextMessage(toId, sendMessage);
  }

  private void sendTextMessage(Integer userId, String message) {
    if (CurrPool.currSessions.containsKey(userId)) {
      Session session = (Session) CurrPool.currSessions.get(userId).get(1);
      if (session != null) {
        try {
          session.getBasicRemote().sendText(message);
        } catch (IOException ioException) {
          System.err.println(ioException.getMessage());
        }
      }
    }

  }
}
