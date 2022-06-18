package com.example.demo.service.impl;

import com.example.demo.dao.SessionChatDao;
import com.example.demo.dto.AjaxResult;
import com.example.demo.entity.SessionChat;
import com.example.demo.service.SessionChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SessionChatServiceImpl implements SessionChatService {

  @Autowired
  private SessionChatDao sessionChatDao;

  @Override
  public AjaxResult<?> existSession(Integer userId) {
    List<SessionChat> sessionChats = sessionChatDao.selectByUserId(userId);
    return AjaxResult.success(sessionChats);
  }

  @Override
  public AjaxResult<?> createSession(SessionChat sessionChat) {

    Integer userId = sessionChat.getUserId();
    Integer toUserId = sessionChat.getToUserId();
    Integer sessionType = sessionChat.getSessionType();
    SessionChat selectSessionChat = sessionChatDao.selectByUserIdAndToUserId(userId, toUserId);
    if (selectSessionChat != null) {
      return AjaxResult.failure("当前会话已经存在");
    } else {
      SessionChat chat = new SessionChat();
      chat.setUserId(userId);
      chat.setToUserId(toUserId);
      chat.setSessionType(sessionType);
      chat.setSessionName(sessionChat.getSessionName());
      chat.setUnReadCount(0);
      int insert = sessionChatDao.insert(chat);
      if (insert > 0) {
        //判断当前会话是群聊会话还是私发会话，对方是否有当前会话，如果没有，也要添加一个会话进去
        if (sessionType == 1) {
          chat.setUserId(toUserId);
          chat.setToUserId(userId);
          sessionChatDao.insert(chat);
        }
        return AjaxResult.success("创建成功");
      } else
        return AjaxResult.failure();
    }
  }

  @Override
  public AjaxResult<?> deleteSession(Integer sessionId) {
    int delete = sessionChatDao.deleteByPrimaryKey(sessionId);
    if (delete > 0) {
      return AjaxResult.success("删除成功");
    } else
      return AjaxResult.failure();
  }

  @Override
  public AjaxResult<?> unReadCountToZero(Integer userId, Integer toUserId) {

    int update = sessionChatDao.unReadCountTOZero(userId, toUserId);
    if (update > 0) {
      return AjaxResult.success();
    } else {
      return AjaxResult.failure();
    }
  }
}
