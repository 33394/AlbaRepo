package com.example.demo.dao;

import com.example.demo.entity.Friend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Friend record);

    int insertSelective(Friend record);

    Friend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Friend record);

    int updateByPrimaryKey(Friend record);

    List<Friend> getFriends(Integer addId);

    Integer getFriend(Integer addId, Integer acceptId);

    Integer changePassStatus(Integer id, Integer status);
}