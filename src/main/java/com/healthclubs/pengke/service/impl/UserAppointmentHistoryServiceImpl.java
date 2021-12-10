package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.UserAppointmentHistory;
import com.healthclubs.pengke.entity.UserInfo;
import com.healthclubs.pengke.mapper.IUserAppointmentHistoryMapper;
import com.healthclubs.pengke.service.IUserAppointmentHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAppointmentHistoryServiceImpl extends ServiceImpl<IUserAppointmentHistoryMapper, UserAppointmentHistory>
        implements IUserAppointmentHistoryService {


    @Override
    public List<UserInfo> getAppointMembersByDates(Integer days, String coachId, Integer customLevel) {
        return baseMapper.getAppointMembersByDates(days,coachId,customLevel);
    }
}
