package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.UserTrainingPlan;
import com.healthclubs.pengke.mapper.IUserTrainingPlanMapper;
import com.healthclubs.pengke.service.IUserTrainingPlanService;
import org.springframework.stereotype.Service;

@Service
public class UserTrainingPlanServiceImpl extends ServiceImpl<IUserTrainingPlanMapper, UserTrainingPlan>
        implements IUserTrainingPlanService {
}
