package com.example.demo.service;

import com.example.demo.dto.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

  AjaxResult<?> upload(MultipartFile file, Integer fromId, Integer toId);

  AjaxResult<?> changeHead(MultipartFile file, Integer id);
}
