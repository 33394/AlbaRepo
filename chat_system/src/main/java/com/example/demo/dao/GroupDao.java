package com.example.demo.dao;

import com.example.demo.entity.Group;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
}