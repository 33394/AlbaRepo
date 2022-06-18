package com.example.demo.service;

import com.example.demo.dto.AjaxResult;

public interface MessageService {

  AjaxResult<?> getMessages(Integer fromId, Integer toId, Long time);

  AjaxResult<?> getMessagesCount(Integer fromId, Integer toId);

  AjaxResult<?> getMessageById(Integer messageId);
}
