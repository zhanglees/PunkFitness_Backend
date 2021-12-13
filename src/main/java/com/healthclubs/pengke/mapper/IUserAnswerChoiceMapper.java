package com.healthclubs.pengke.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthclubs.pengke.entity.UserAnswerChoice;

import java.util.List;

public interface IUserAnswerChoiceMapper extends BaseMapper<UserAnswerChoice> {

    public List<UserAnswerChoice> getUserQuestionList(String userId);

}
