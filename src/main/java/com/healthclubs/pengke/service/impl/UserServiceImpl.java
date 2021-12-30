package com.healthclubs.pengke.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.healthclubs.pengke.entity.StudentAndCoach;
import com.healthclubs.pengke.entity.UserInfo;
import com.healthclubs.pengke.mapper.IUserMapper;
import com.healthclubs.pengke.pojo.dto.RegisterDto;
import com.healthclubs.pengke.pojo.dto.WiXiFormViewDto;
import com.healthclubs.pengke.pojo.dto.WiXiLoginReturnDto;
import com.healthclubs.pengke.service.IStudentAndCoachService;
import com.healthclubs.pengke.service.IUserService;
import com.healthclubs.pengke.utils.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, UserInfo>
        implements IUserService {

    @Autowired
    private IStudentAndCoachService studentAndCoach;

    @Autowired
    private CacheService cacheService;


    /** 版本key */
    @Value("${appsetings.version-key}")
    private String versionKey;

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
            //默认准会员
            if(usermodel.getCoustomLevel()==null)
            {
                usermodel.setCoustomLevel(0);
            }
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

    /**
     * 根据教练id得到 教练管理的学员
     */
    @Override
    public List<UserInfo> getTrainerInfoByCoachId(String coachId) {

        try
        {
            List<UserInfo> userData = baseMapper.getTrainerByCoachId(coachId);
            return  userData;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public List<UserInfo> getTrainerInfoByCoachIdAndlevel(String coachId, Integer customLevel) {
        try
        {
            List<UserInfo> userData = baseMapper.getTrainerInfoByCoachIdAndlevel(coachId,customLevel);
            return  userData;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public List<UserInfo> getAppointmentTrainers(String coachId) {
        try
        {
            List<UserInfo> userData = baseMapper.getAppointmentTrainers(coachId);
            return  userData;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public WiXiFormViewDto wixiLogin(WiXiLoginReturnDto data) {

        try
        {
            UserInfo userInfo;
            userInfo = this.getOne(new QueryWrapper<UserInfo>().eq("wxid",data.getOpenid()));
            if(userInfo==null)
            {
                userInfo = new UserInfo();
                userInfo.setId(UUID.randomUUID().toString());
                userInfo.setWxid(data.getOpenid());
                userInfo.setUserName("System"+System.currentTimeMillis());
                userInfo.setSessionKey(data.getSession_key());
                userInfo.setVersionKey(versionKey);
                this.save(userInfo);

            }
            else
            {
                userInfo.setSessionKey(data.getSession_key());
                this.updateById(userInfo);

            }

            cacheService.add(data.session_key,userInfo,30, TimeUnit.MINUTES);
            //UserInfo temp = cacheService.getObject(data.session_key,UserInfo.class);

            WiXiFormViewDto wiXiFormViewDto = new WiXiFormViewDto();
            wiXiFormViewDto.userId = userInfo.getId();
            wiXiFormViewDto.session_key = data.session_key;
            wiXiFormViewDto.setVersionKey(versionKey);

            return wiXiFormViewDto;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public List<UserInfo> getUsersByCoachId(String coachId) {

        try
        {
            return baseMapper.getUsersByCoachId(coachId);
        }
        catch (Exception ex){
            throw ex;
        }


    }

    @Override
    public List<UserInfo> getUsersByCoachIdAndlevel(String coachId, Integer customLevel) {

        try
        {
            return baseMapper.getUsersByCoachIdAndlevel(coachId,customLevel);
        }
        catch (Exception ex){
            throw ex;
        }

    }


}
