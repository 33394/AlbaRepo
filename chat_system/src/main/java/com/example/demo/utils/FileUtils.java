package com.example.demo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {

  public static void main(String[] args) {

    String path = System.getProperty("user.dir");
    path = path.replace("\\", "/");
    System.out.println(path);
    System.out.println(UUID.randomUUID());
  }

  public static boolean save(MultipartFile file, String path, String fileName) {

    folderIsExist(path);
    File fileSave = new File(path + fileName);
    try {
      file.transferTo(fileSave);
    } catch (IOException e) {
      System.err.println(e.getMessage());
      return false;
    }
    return true;

  }

  //判断文件夹是否存在，不存在就创建
  private static void folderIsExist(String path) {

    String[] paths = path.split("/");
    String dir = paths[0];

    for (int i = 1; i < paths.length; i++) {
      try {
        dir += "/" + paths[i];
        File file = new File(dir);
        if (!file.exists()) {
          boolean mkdir = file.mkdir();
        }
      } catch (Exception e) {
        System.err.println("创建文件夹失败" + e.getMessage());
      }

    }
  }
}
