package com.example.demo.service;

import com.example.demo.dto.AjaxResult;
import com.example.demo.entity.SessionChat;

public interface SessionChatService {

  AjaxResult<?> existSession(Integer userId);

  AjaxResult<?> createSession(SessionChat sessionChat);

  AjaxResult<?> deleteSession(Integer sessionId);

  AjaxResult<?> unReadCountToZero(Integer userId, Integer toUserId);
}
