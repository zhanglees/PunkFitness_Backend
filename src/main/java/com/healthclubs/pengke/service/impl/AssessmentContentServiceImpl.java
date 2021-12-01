package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.AssessmentContent;
import com.healthclubs.pengke.mapper.IAssessmentContentMapper;
import com.healthclubs.pengke.service.IAssessmentContentService;
import org.springframework.stereotype.Service;

@Service
public class AssessmentContentServiceImpl extends ServiceImpl<IAssessmentContentMapper, AssessmentContent>
        implements IAssessmentContentService {
}
