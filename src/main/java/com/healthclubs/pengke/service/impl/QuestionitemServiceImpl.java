package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.Questionitem;
import com.healthclubs.pengke.mapper.IQuestionitemMapper;
import com.healthclubs.pengke.service.IQuestionitemService;
import org.springframework.stereotype.Service;

@Service
public class QuestionitemServiceImpl extends ServiceImpl<IQuestionitemMapper, Questionitem>
        implements IQuestionitemService {
}
