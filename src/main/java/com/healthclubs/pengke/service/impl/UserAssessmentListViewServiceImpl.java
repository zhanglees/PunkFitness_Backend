package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.UserAssessmentListView;
import com.healthclubs.pengke.mapper.IUserAssessmentListViewMapper;
import com.healthclubs.pengke.service.IUserAssessmentListViewService;
import org.springframework.stereotype.Service;

@Service
public class UserAssessmentListViewServiceImpl extends ServiceImpl<IUserAssessmentListViewMapper, UserAssessmentListView>
        implements IUserAssessmentListViewService {
}
