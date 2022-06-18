package com.example.demo.controller;

import com.example.demo.dto.AjaxResult;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {


  @Autowired
  private MessageService messageService;

  @RequestMapping("/getMessages")
  public AjaxResult<?> getMessages(@RequestBody Map<String, Long> messageInfo) {

    return messageService.getMessages(Integer.parseInt(messageInfo.get("fromId") + ""),
            Integer.parseInt("" + messageInfo.get("toId")), messageInfo.get("time"));
  }

  @RequestMapping("/getMessagesCount")
  public AjaxResult<?> getMessagesCount(@RequestBody Map<String, Integer> message) {

    return messageService.getMessagesCount(message.get("fromId"), message.get("toId"));
  }

  @RequestMapping("/getMessageById")
  public AjaxResult<?> getMessageById(@RequestBody Map<String, Integer> messageId) {

    return messageService.getMessageById(messageId.get("id"));
  }
}
