package com.healthclubs.pengke.controller;

import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.utils.I18nService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {


    @Autowired
    protected I18nService i18nService;

    /**
     * 获取请求响应结果
     * @param errCode 状态码
     * @return 请求响应
     */
    <T> Result<T> getResult(int errCode){
        String errMessage = i18nService.getMessage(ResponseCode.getMessageKeyByCode(errCode));
        return new Result(errCode, errMessage, null);
    }


    /**
     * 获取请求响应结果
     * @param errCode 状态码
     * @param <T> 响应数据类型
     * @return 请求响应
     */
    <T> Result<T> getResult(int errCode,T data){
        String errMessage = i18nService.getMessage(ResponseCode.getMessageKeyByCode(errCode));
        return new Result(errCode, errMessage, data);
    }

}
