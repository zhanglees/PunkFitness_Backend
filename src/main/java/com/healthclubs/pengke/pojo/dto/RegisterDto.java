package com.healthclubs.pengke.pojo.dto;

import com.healthclubs.pengke.entity.UserInfo;

public class RegisterDto extends UserInfo {

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    private  String teacherId;
}
