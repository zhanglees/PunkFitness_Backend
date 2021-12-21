package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.ClassContent;
import com.healthclubs.pengke.mapper.IClassContentMapper;
import com.healthclubs.pengke.service.IClassContentService;
import org.springframework.stereotype.Service;

@Service
public class ClassContentServiceImpl extends ServiceImpl<IClassContentMapper, ClassContent>
        implements IClassContentService {
}
