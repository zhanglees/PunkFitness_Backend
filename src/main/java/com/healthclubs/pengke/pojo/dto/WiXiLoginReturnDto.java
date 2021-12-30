package com.healthclubs.pengke.pojo.dto;

public class WiXiLoginReturnDto {

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String  openid;

    public String session_key;

    public String unionid;

    public String errcode = "0";

    public String errmsg;


   // public WiXinUserInfoDto getWiXinUserInfoDto() {
    //    return wiXinUserInfoDto;
   // }

   // public void setWiXinUserInfoDto(WiXinUserInfoDto wiXinUserInfoDto) {
    //    this.wiXinUserInfoDto = wiXinUserInfoDto;
   // }

   // public WiXinUserInfoDto wiXinUserInfoDto;
}
