package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.UserAssessmentFeedback;
import com.healthclubs.pengke.mapper.IUserAssessmentFeedbackMapper;
import com.healthclubs.pengke.service.IUserAssessmentFeedbackService;
import org.springframework.stereotype.Service;

@Service
public class UserAssessmentFeedbackServiceImpl extends ServiceImpl<IUserAssessmentFeedbackMapper, UserAssessmentFeedback>
        implements IUserAssessmentFeedbackService {
}
