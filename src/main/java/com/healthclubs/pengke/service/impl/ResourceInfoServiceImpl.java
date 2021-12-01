package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.ResourceInfo;
import com.healthclubs.pengke.mapper.IResourceInfoMapper;
import com.healthclubs.pengke.service.IResourceInfoService;
import org.springframework.stereotype.Service;

@Service
public class ResourceInfoServiceImpl extends ServiceImpl<IResourceInfoMapper, ResourceInfo>
        implements IResourceInfoService {
}
