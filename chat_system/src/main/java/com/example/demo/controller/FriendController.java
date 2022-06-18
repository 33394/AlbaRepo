package com.example.demo.controller;

import com.example.demo.dto.AjaxResult;
import com.example.demo.entity.Friend;
import com.example.demo.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/friend")
public class FriendController {



  @Autowired
  private FriendService friendService;

  @RequestMapping("/getFriends")
  public AjaxResult<?> getFriends(@RequestBody Map<String, Integer> map) {
    return friendService.getFriends(map.get("id"));
  }

  @RequestMapping("/addFriend")
  public AjaxResult<?> addFriend(@RequestBody Friend friend) {
    return friendService.addFriend(friend);
  }

  @RequestMapping("/changePassStatus")
  public AjaxResult<?> changePassStatus(@RequestBody Map<String, Integer> friend) {

    return friendService.changePassStatus(friend.get("id"), friend.get("status"));
  }

  @RequestMapping("/deleteFriend")
  public AjaxResult<?> deleteFriend(@RequestBody Map<String, Integer> friendId) {

    return friendService.deleteFriend(friendId.get("id"));
  }
}
