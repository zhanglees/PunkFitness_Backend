package com.healthclubs.pengke.pojo.dto;

public class WiXiFormViewDto extends WiXiLoginReturnDto{

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String userId;

    public String getVersionKey() {
        return versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    public String versionKey;
}
