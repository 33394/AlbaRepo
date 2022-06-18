package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.dto.AjaxResult;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.CurrPool;
import com.example.demo.utils.QQNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  @Override
  public AjaxResult<?> getQQNumber() {
    Integer id;
    while (true) {
      id = QQNumberUtil.getQQNumber();
      //确保QQ号唯一
      User select = userDao.selectByPrimaryKey(id);
      if (select == null)
        break;
    }
    return AjaxResult.success(id);
  }

  @Override
  public AjaxResult<?> register(User user) {
    User select = userDao.selectByPrimaryKey(user.getId());
    if (select != null)
      return AjaxResult.failure("当前QQ账户已被使用，请重新申请QQ号");
    int insert = userDao.insert(user);
    if (insert > 0) {
      AjaxResult<User> ajaxResult = new AjaxResult<>();
      ajaxResult.setSuccess(true);
      ajaxResult.setData(user);
      ajaxResult.setMsg("注册成功");
      return ajaxResult;
    } else
      return AjaxResult.failure("注册失败,系统繁忙");

  }

  @Override
  public AjaxResult<?> login(Integer userId, String password) {
    if (CurrPool.currUsers.get(userId) != null) {
      return AjaxResult.failure("当前用户已经登录");
    } else {
      User user = userDao.selectByPrimaryKey(userId);
      if (user != null) {
        if (user.getPassword().equals(password))
          return AjaxResult.success(user);
        else
          return AjaxResult.failure("密码错误");
      } else
        return AjaxResult.failure("当前用户不存在");
    }
  }

  //退出登录
  @Override
  public AjaxResult<?> logout(Integer userId) {
    User user = userDao.selectByPrimaryKey(userId);
    if (user == null) {
      return AjaxResult.failure("当前账户不存在");
    } else {
      CurrPool.currUsers.remove(userId);
      CurrPool.webSockets.remove(user.getId());
      CurrPool.currSessions.remove(user.getId());
      return AjaxResult.success("退出登录成功");
    }

  }

  //注销
  @Override
  public AjaxResult<?> unsubscribe(Integer userId) {

    AjaxResult<?> logout = logout(userId);
    if (logout.getSuccess()) {
      int delete = userDao.deleteByPrimaryKey(userId);
      if (delete > 0)
        return AjaxResult.success("账号注销成功");
      else
        return AjaxResult.failure("注销失败，系统繁忙");
    }
    return AjaxResult.failure("当前账户不存在");
  }

  @Override
  public AjaxResult<?> getCurrUser(Integer userId) {

    if (userId != null && userId > 0) {
      User user = userDao.selectByPrimaryKey(userId);
      if (user != null)
        return AjaxResult.success(user);
      else return AjaxResult.failure("当前用户不存在");
    }
    return AjaxResult.failure("你的用户名输入有误");
  }

  @Override
  public AjaxResult<?> searchUser(Integer userInfo) {

    User user = userDao.searchUser(userInfo);
    if (user != null)
      return AjaxResult.success(user);
    else
      return AjaxResult.failure("你搜索的用户不存在");

  }

  @Override
  public AjaxResult<?> changeHead(MultipartFile file, Integer id) {
    return null;
  }
}
