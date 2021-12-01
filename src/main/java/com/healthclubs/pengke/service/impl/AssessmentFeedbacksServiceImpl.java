package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.AssessmentFeedbacks;
import com.healthclubs.pengke.mapper.IAssessmentFeedbacksMapper;
import com.healthclubs.pengke.service.IAssessmentFeedbacksService;
import org.springframework.stereotype.Service;

@Service
public class AssessmentFeedbacksServiceImpl extends ServiceImpl<IAssessmentFeedbacksMapper, AssessmentFeedbacks>
        implements IAssessmentFeedbacksService {
}
