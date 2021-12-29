package com.healthclubs.pengke.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthclubs.pengke.entity.UserInfo;

import java.util.List;


public interface IUserMapper extends BaseMapper<UserInfo> {

    List<UserInfo> getUserAll();

    List<UserInfo> getTrainerByCoachId(String coachId);

    List<UserInfo> getTrainerInfoByCoachIdAndlevel(String coachId,Integer customLevel);

    List<UserInfo> getAppointmentTrainers(String coachId);
}
