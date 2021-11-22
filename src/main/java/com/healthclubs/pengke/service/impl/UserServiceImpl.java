package com.healthclubs.pengke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.StudentAndCoach;
import com.healthclubs.pengke.entity.UserInfo;
import com.healthclubs.pengke.mapper.IUserMapper;
import com.healthclubs.pengke.pojo.dto.RegisterDto;
import com.healthclubs.pengke.service.IStudentAndCoachService;
import com.healthclubs.pengke.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, UserInfo>
        implements IUserService {


    @Autowired
    private IStudentAndCoachService studentAndCoach;

    public UserServiceImpl() {
    }

    @Override
    public List<UserInfo> getUserAll() {
        return baseMapper.getUserAll();
    }

    @Override
    @Transactional
    public RegisterDto register(RegisterDto usermodel) {

        try {
            baseMapper.insert(usermodel);
            StudentAndCoach _stuCoach = new StudentAndCoach();
            _stuCoach.setCoachId(usermodel.getTeacherId());
            _stuCoach.setStudentId(usermodel.getId());
            _stuCoach.setRecordTime(new Date());
            studentAndCoach.save(_stuCoach);

        } catch (Exception e) {
            throw e;
        }
        return  usermodel;
    }


}
