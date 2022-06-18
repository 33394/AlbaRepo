package com.example.demo.dao;

import com.example.demo.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface MessageDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKeyWithBLOBs(Message record);

    int updateByPrimaryKey(Message record);

    List<Message> getMessages(Integer fromId, Integer toId);

    List<Message> getMessagesByTime(Integer fromId, Integer toId, Date time);

    List<Message> getMessagesByTimeCurr(Integer fromId, Integer toId, Date time);

    Integer getMessagesCount(Integer fromId, Integer toId);
}