package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.UserAppointment;
import com.healthclubs.pengke.mapper.IUserAppointmentMapper;
import com.healthclubs.pengke.service.IUserAppointmentService;
import org.springframework.stereotype.Service;

@Service
public class UserAppointmentServiceImpl extends ServiceImpl<IUserAppointmentMapper, UserAppointment>
        implements IUserAppointmentService {
}
