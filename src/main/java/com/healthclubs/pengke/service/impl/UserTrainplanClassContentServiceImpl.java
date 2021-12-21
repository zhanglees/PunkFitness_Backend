package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.UserTrainplanClassContent;
import com.healthclubs.pengke.mapper.IUserTrainplanClassContentMapper;
import com.healthclubs.pengke.service.IUserTrainplanClassContentService;
import org.springframework.stereotype.Service;

@Service
public class UserTrainplanClassContentServiceImpl extends ServiceImpl<IUserTrainplanClassContentMapper, UserTrainplanClassContent>
        implements IUserTrainplanClassContentService {
}
