package com.example.demo.service;

import com.example.demo.dto.AjaxResult;
import com.example.demo.entity.Friend;

public interface FriendService {

  AjaxResult<?> getFriends(Integer userId);

  AjaxResult<?> addFriend(Friend friend);

  AjaxResult<?> changePassStatus(Integer id, Integer status);

  AjaxResult<?> deleteFriend(Integer friendId);
}
