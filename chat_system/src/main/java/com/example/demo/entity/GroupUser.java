package com.example.demo.entity;

import java.util.Date;

public class GroupUser {
    private Integer id;

    private Integer userId;

    private Integer groupId;

    private String groupPosition;

    private Date addTime;

    private Integer passStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupPosition() {
        return groupPosition;
    }

    public void setGroupPosition(String groupPosition) {
        this.groupPosition = groupPosition == null ? null : groupPosition.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(Integer passStatus) {
        this.passStatus = passStatus;
    }
}