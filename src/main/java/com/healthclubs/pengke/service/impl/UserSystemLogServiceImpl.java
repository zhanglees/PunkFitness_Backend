package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.UserSystemLog;
import com.healthclubs.pengke.mapper.IUserSystemLogMapper;
import com.healthclubs.pengke.service.IUserSystemLogService;
import org.springframework.stereotype.Service;

@Service
public class UserSystemLogServiceImpl  extends ServiceImpl<IUserSystemLogMapper, UserSystemLog>
        implements IUserSystemLogService {
}
