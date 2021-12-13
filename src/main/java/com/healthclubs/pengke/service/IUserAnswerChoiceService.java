package com.healthclubs.pengke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthclubs.pengke.entity.UserAnswerChoice;

import java.util.List;

public interface IUserAnswerChoiceService extends IService<UserAnswerChoice> {

    public List<UserAnswerChoice> getUserQuestionList(String userId);
}
