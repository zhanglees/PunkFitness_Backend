package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.UserTrainItem;
import com.healthclubs.pengke.mapper.IUserTrainItemMapper;
import com.healthclubs.pengke.service.IUserTrainItemService;
import org.springframework.stereotype.Service;

@Service
public class UserTrainItemServiceImpl extends ServiceImpl<IUserTrainItemMapper, UserTrainItem>
        implements IUserTrainItemService {
}
