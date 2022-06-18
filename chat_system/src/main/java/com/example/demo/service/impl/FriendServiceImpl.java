package com.example.demo.service.impl;

import com.example.demo.dao.FriendDao;
import com.example.demo.dao.SessionChatDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.AjaxResult;
import com.example.demo.entity.Friend;
import com.example.demo.entity.User;
import com.example.demo.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FriendServiceImpl implements FriendService {


  @Autowired
  private FriendDao friendDao;

  @Autowired
  private UserDao userDao;

  @Autowired
  SessionChatDao sessionChatDao;


  @Override
  public AjaxResult<?> getFriends(Integer userId) {
    List<Friend> Friends = friendDao.getFriends(userId);
    if (Friends == null || Friends.isEmpty()) {
      return AjaxResult.success("你当前还没有好友呦，快去添加吧");
    } else
      return AjaxResult.success(Friends);
  }

  @Override
  public AjaxResult<?> addFriend(Friend Friend) {

    Integer searchFriend = friendDao.getFriend(Friend.getAddId(), Friend.getAcceptId());
    if (searchFriend > 0) {
      return AjaxResult.failure("你们已经是好友了");
    } else {
      User addUser = userDao.selectByPrimaryKey(Friend.getAddId());
      User acceptUser = userDao.selectByPrimaryKey(Friend.getAcceptId());
      Friend.setAddName(addUser.getName());
      Friend.setAcceptName(acceptUser.getName());
      int insert = friendDao.insert(Friend);
      if (insert > 0) {
        return AjaxResult.success("请求提交成功");
      } else {
        return AjaxResult.success(("添加失败"));
      }
    }
  }

  @Override
  public AjaxResult<?> changePassStatus(Integer id, Integer status) {
    Integer change = friendDao.changePassStatus(id, status);
    if (change > 0) {
      return AjaxResult.success();
    } else
      return AjaxResult.failure("系统繁忙");
  }

  @Override
  public AjaxResult<?> deleteFriend(Integer friendId) {

    Friend friend = friendDao.selectByPrimaryKey(friendId);
    sessionChatDao.deleteByFriend(friend.getAddId(), friend.getAcceptId());
    int delete = friendDao.deleteByPrimaryKey(friendId);
    if (delete > 0) {
      return AjaxResult.success("删除成功");
    } else {
      return AjaxResult.failure("删除失败");
    }

  }
}
