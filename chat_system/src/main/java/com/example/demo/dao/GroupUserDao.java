package com.example.demo.dao;

import com.example.demo.entity.GroupUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupUser record);

    int insertSelective(GroupUser record);

    GroupUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupUser record);

    int updateByPrimaryKey(GroupUser record);
}