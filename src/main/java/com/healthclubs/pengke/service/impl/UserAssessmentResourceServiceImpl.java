package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.UserAssessmentResource;
import com.healthclubs.pengke.mapper.IUserAssessmentResourceMapper;
import com.healthclubs.pengke.service.IUserAssessmentResourceService;
import org.springframework.stereotype.Service;

@Service
public class UserAssessmentResourceServiceImpl extends ServiceImpl<IUserAssessmentResourceMapper, UserAssessmentResource>
        implements IUserAssessmentResourceService {
}
