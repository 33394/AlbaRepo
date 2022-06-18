package com.example.demo.service.impl;

import com.example.demo.dao.MessageDao;
import com.example.demo.dto.AjaxResult;
import com.example.demo.entity.Message;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

  @Autowired
  private MessageDao messageDao;

  @Override
  public AjaxResult<?> getMessages(Integer fromId, Integer toId, Long time) {

    List<Message> messages;
    if (time == null || time == 0) {
      messages = messageDao.getMessages(fromId, toId);
    } else {
      messages = messageDao.getMessagesByTime(fromId, toId, new Date(time));
      if (messages == null || messages.isEmpty()) {
        messages = messageDao.getMessagesByTimeCurr(fromId, toId, new Date(time));
      }
    }
    if (messages == null || messages.isEmpty()) {
      return AjaxResult.failure("没有可查询到的消息");
    } else {
      List<Message> collect = messages.stream().sorted(Comparator.comparing(Message::getCreateTime)).collect(Collectors.toList());
      return AjaxResult.success(collect);
    }
  }

  @Override
  public AjaxResult<?> getMessagesCount(Integer fromId, Integer toId) {
    Integer messages = messageDao.getMessagesCount(fromId, toId);

    return AjaxResult.success(messages);
  }

  @Override
  public AjaxResult<?> getMessageById(Integer messageId) {

    Message message = messageDao.selectByPrimaryKey(messageId);
    return AjaxResult.success(message);
  }
}
