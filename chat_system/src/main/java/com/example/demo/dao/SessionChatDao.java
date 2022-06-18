package com.example.demo.dao;

import com.example.demo.entity.SessionChat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SessionChatDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SessionChat record);

    int insertSelective(SessionChat record);

    SessionChat selectByPrimaryKey(Integer id);

    SessionChat selectByUserIdAndToUserId(Integer userId, Integer toUserId);

    List<SessionChat> selectByUserId(Integer userId);

    int addUnReadCount(Integer userId, Integer toUserId);

    int updateByPrimaryKeySelective(SessionChat record);

    int updateByPrimaryKey(SessionChat record);

    int unReadCountTOZero(Integer userId, Integer toUserId);

    int deleteByFriend(Integer userId, Integer toUserId);
}