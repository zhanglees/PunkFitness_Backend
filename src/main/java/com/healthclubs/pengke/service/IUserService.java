package com.healthclubs.pengke.service;

import com.healthclubs.pengke.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.healthclubs.pengke.pojo.dto.RegisterDto;

import java.util.List;


public interface IUserService  extends IService<UserInfo>{
    List<UserInfo> getUserAll();

    RegisterDto register(RegisterDto usermodel);

    List<UserInfo> getTrainerInfoByCoachId(String coachId);

    List<UserInfo> getTrainerInfoByCoachIdAndlevel(String coachId,Integer customLevel);
}
