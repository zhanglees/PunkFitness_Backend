package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.UserhealthcheckReport;
import com.healthclubs.pengke.mapper.IUserhealthcheckReportMapper;
import com.healthclubs.pengke.service.IUserhealthcheckReportService;
import org.springframework.stereotype.Service;

@Service
public class UserhealthcheckReportServiceImpl extends ServiceImpl<IUserhealthcheckReportMapper, UserhealthcheckReport>
        implements IUserhealthcheckReportService {
}
