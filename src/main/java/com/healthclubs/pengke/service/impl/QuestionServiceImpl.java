package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.Question;
import com.healthclubs.pengke.mapper.IQuestionMapper;
import com.healthclubs.pengke.service.IQuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl  extends ServiceImpl<IQuestionMapper, Question>
        implements IQuestionService {
}
