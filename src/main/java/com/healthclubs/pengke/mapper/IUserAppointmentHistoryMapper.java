package com.healthclubs.pengke.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthclubs.pengke.entity.UserAppointmentHistory;
import com.healthclubs.pengke.entity.UserInfo;

import java.util.List;

public interface IUserAppointmentHistoryMapper extends BaseMapper<UserAppointmentHistory> {

    List<UserInfo> getAppointMembersByDates(Integer days,String coachId,Integer customLevel);
}
