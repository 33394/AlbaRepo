package com.example.demo.service;

import com.example.demo.dto.AjaxResult;
import com.example.demo.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  AjaxResult<?> getQQNumber();

  AjaxResult<?> register(User user);

  AjaxResult<?> login(Integer userName, String password);

  AjaxResult<?> logout(Integer userName);

  AjaxResult<?> unsubscribe(Integer userName);

  AjaxResult<?> getCurrUser(Integer userId);

  AjaxResult<?> searchUser(Integer userId);

  AjaxResult<?> changeHead(MultipartFile file, Integer id);
}
