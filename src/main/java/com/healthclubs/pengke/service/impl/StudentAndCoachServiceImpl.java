package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.StudentAndCoach;
import com.healthclubs.pengke.mapper.IStudentAndCoachMapper;
import com.healthclubs.pengke.service.IStudentAndCoachService;
import org.springframework.stereotype.Service;


@Service
public class StudentAndCoachServiceImpl
        extends ServiceImpl<IStudentAndCoachMapper, StudentAndCoach>
        implements IStudentAndCoachService {
}
