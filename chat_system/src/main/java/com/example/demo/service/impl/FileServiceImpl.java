package com.example.demo.service.impl;

import com.example.demo.dao.MessageDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.AjaxResult;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.service.FileService;
import com.example.demo.service.WebSocketService;
import com.example.demo.utils.FileUtils;
import com.example.demo.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
public class FileServiceImpl implements FileService {

  @Autowired
  private UserDao userDao;

  @Autowired
  private MessageDao messageDao;

  @Autowired
  private WebSocketService webSocketService;

  @Override
  public AjaxResult<?> upload(MultipartFile file, Integer fromId, Integer toId) {
    if (file != null) {
      String originalFilename = file.getOriginalFilename();
      if (originalFilename != null) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase(Locale.ROOT);

        String filePath = createFilePath(toId);
        String fileName = createFileName(originalFilename);
        boolean save = FileUtils.save(file, filePath,
                fileName.substring(0, fileName.indexOf("_")) + "." + extension);
        if (save) {

          Message message = new Message();
          User fromUser = userDao.selectByPrimaryKey(fromId);
          User toUser = userDao.selectByPrimaryKey(toId);
          //判断是否是图片类型文件
          if (extension.equals("jpg") || extension.equals("bmp") || extension.equals("png") ||
                  extension.equals("svg") || extension.equals("gif"))
            message.setType(2);
          else
            message.setType(3);
          message.setCreateTime(TimeUtil.formatDate(new Date()));
          message.setReadStatus(0);
          String fileStaticPath = filePath.replaceFirst(getSystemDir() + getFileLoadDir(), "");
          message.setContent("files/" + fileStaticPath + fileName);
          message.setFromId(fromUser.getId());
          message.setToId(toUser.getId());
          message.setGroupOrFriend(1);
          message.setFromName(fromUser.getName());
          message.setToName(toUser.getName());
          messageDao.insert(message);
          AjaxResult<Message> ajaxResult = new AjaxResult<>();
          ajaxResult.setCode(10);
          ajaxResult.setData(message);
          ajaxResult.setSuccess(true);
          ajaxResult.setMsg("上传成功");
          webSocketService.sendMessage(toUser.getId(), message);
          return ajaxResult;
        }
      }
    }

    return null;
  }

  @Override
  public AjaxResult<?> changeHead(MultipartFile file, Integer id) {
    if (file != null) {
      String originalFilename = file.getOriginalFilename();
      if (originalFilename != null) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase(Locale.ROOT);
        if (extension.equals("jpg") || extension.equals("bmp") || extension.equals("png") ||
                extension.equals("svg") || extension.equals("gif")) {
          String filePath = createFilePath(id);
          String fileName = createFileName(originalFilename);
          boolean save = FileUtils.save(file, filePath,
                  fileName.substring(0, fileName.indexOf("_")) + "." + extension);
          if (save) {
            //判断是否是图片类型文件
            String fileStaticPath = filePath.replaceFirst(getSystemDir() + getFileLoadDir(), "");
            fileStaticPath = "files/" + fileStaticPath + fileName;
            userDao.updateHead(fileStaticPath, id);
            return AjaxResult.success("操作成功");
          } else
            return AjaxResult.failure("系统繁忙，操作失败");
        } else return AjaxResult.failure("请上传图片格式文件");
      }
    }

    return null;
  }


  private String createFilePath(Integer toId) {

    return getSystemDir() + getFileLoadDir() + toId + "/";

  }

  private String createFileName(String name) {

    return UUID.randomUUID() + "_" + name;
  }

  private String getSystemDir() {

    //文件存储在资源文件夹下file包里，以接收方来进行文件分包
    String path = System.getProperty("user.dir");
    path = path.replace("\\", "/");
    return path + "/";
  }


  //上传文件存储路径
  private String getFileLoadDir() {
    return "src/main/resources/static/files/";
  }
}
