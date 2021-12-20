package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.UserhealthcheckResource;
import com.healthclubs.pengke.mapper.IUserhealthcheckResourceMapper;
import com.healthclubs.pengke.service.IUserhealthcheckResourceService;
import org.springframework.stereotype.Service;

@Service
public class UserhealthcheckResourceServiceImpl extends ServiceImpl<IUserhealthcheckResourceMapper, UserhealthcheckResource>
        implements IUserhealthcheckResourceService {
}
