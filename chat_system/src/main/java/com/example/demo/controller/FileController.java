package com.example.demo.controller;


import com.example.demo.dto.AjaxResult;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {


  @Autowired
  private FileService fileService;

  @RequestMapping("/upload")
  public AjaxResult<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("fromId") Integer fromId, @RequestParam("toId") Integer toId) {

    return fileService.upload(file, fromId, toId);

  }

  @RequestMapping("/download")
  public AjaxResult<?> download() {
    return null;
  }
}
