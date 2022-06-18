package com.example.demo.controller;


import com.example.demo.dto.AjaxResult;
import com.example.demo.entity.User;
import com.example.demo.service.FileService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private FileService fileService;
  //获取QQ号
  @RequestMapping("/getQQNumber")
  public AjaxResult<?> getQQNumber() {
    return userService.getQQNumber();
  }
  //注册
  @RequestMapping("/register")
  public AjaxResult<?> register(@RequestBody User user) {

    return userService.register(user);
  }

  @RequestMapping("/changeHead")
  public AjaxResult<?> changeHead(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id) {
    return fileService.changeHead(file, id);
  }

  //登录
  @RequestMapping("/login")
  public AjaxResult<?> login(@RequestBody Map<String, String>  map) {
    return userService.login(Integer.parseInt(map.get("id")), map.get("password"));

  }

  @RequestMapping("/getCurrUser")
  public AjaxResult<?> getCurrUser(@RequestBody Map<String, Integer>  map) {
    return userService.getCurrUser(map.get("id"));
  }

  @RequestMapping("/searchUser")
  public AjaxResult<?> searchUser(@RequestBody Map<String, String> map) {
    try {
      Integer userId = Integer.parseInt(map.get("search"));
      return userService.searchUser(userId);

    }catch (Exception e) {
      return AjaxResult.failure("你的输入无信息匹配项，请检查输入");
    }
  }
  //退出登录
  @GetMapping("/logout")
  public AjaxResult<?> logout(@RequestParam(value = "id") Integer userId) {
    return userService.logout(userId);
  }

  @GetMapping("/unsubscribe")
  public AjaxResult<?> unsubscribe(@RequestParam(value = "id") Integer userId) {

    return userService.unsubscribe(userId);
  }
}
