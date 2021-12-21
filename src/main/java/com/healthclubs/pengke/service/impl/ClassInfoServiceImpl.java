package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.ClassInfo;
import com.healthclubs.pengke.mapper.IClassInfoMapper;
import com.healthclubs.pengke.service.IClassInfoService;
import org.springframework.stereotype.Service;

@Service
public class ClassInfoServiceImpl extends ServiceImpl<IClassInfoMapper, ClassInfo>
        implements IClassInfoService {
}
