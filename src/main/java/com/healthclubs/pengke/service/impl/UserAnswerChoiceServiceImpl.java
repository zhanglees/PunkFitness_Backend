package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.UserAnswerChoice;
import com.healthclubs.pengke.mapper.IUserAnswerChoiceMapper;
import com.healthclubs.pengke.service.IUserAnswerChoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAnswerChoiceServiceImpl   extends ServiceImpl<IUserAnswerChoiceMapper, UserAnswerChoice>
        implements IUserAnswerChoiceService {

    @Override
    public List<UserAnswerChoice> getUserQuestionList(String userId) {
        return baseMapper.getUserQuestionList(userId);
    }

    @Override
    public List<UserAnswerChoice> getUserQuestionListByType(String userId) {
        return baseMapper.getUserQuestionListByType(userId);
    }

    @Override
    public List<UserAnswerChoice> getUserQuestionExplain(String userId) {
        return baseMapper.getUserQuestionExplain(userId);
    }
}
