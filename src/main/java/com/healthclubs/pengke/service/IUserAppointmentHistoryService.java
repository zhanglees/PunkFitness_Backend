package com.healthclubs.pengke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthclubs.pengke.entity.UserAppointmentHistory;
import com.healthclubs.pengke.entity.UserInfo;

import java.util.List;

public interface IUserAppointmentHistoryService extends IService<UserAppointmentHistory> {

    List<UserInfo> getAppointMembersByDates(Integer days,String coachId,Integer customLevel);
}
