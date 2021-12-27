package com.healthclubs.pengke.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.UserAppointment;
import com.healthclubs.pengke.entity.UserAppointmentHistory;
import com.healthclubs.pengke.entity.UserInfo;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.AppointmentDto;
import com.healthclubs.pengke.pojo.dto.AppointmentMembersViewDto;
import com.healthclubs.pengke.pojo.dto.SingInDto;
import com.healthclubs.pengke.service.IUserAppointmentHistoryService;
import com.healthclubs.pengke.service.IUserAppointmentService;
import com.healthclubs.pengke.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Api("coachAppointment")
@RequestMapping("/api/coachAppointment")
public class CoachAppointmentController extends BaseController {

    @Autowired
    IUserAppointmentService userAppointmentService;

    @Autowired
    IUserAppointmentHistoryService userAppointmentHistoryService;

    @Autowired
    IUserService userService;

    //预约
    @ApiOperation(value = "/appointment", notes = "预约")
    @PostMapping(value = "/appointment")
    public Result appointment(@RequestBody AppointmentDto appointmentDto) {
        try {

            Date dateNow = new Date();
            if (appointmentDto.getAppointmentTime().getTime() < dateNow.getTime()) {
                return getResult(ResponseCode.APPOINTTIME_BE_OVERDUE, appointmentDto);
            } else {

                appointmentDto.setAppointmentId(UUID.randomUUID().toString());
                appointmentDto.setCreateTime(new Date());
                userAppointmentService.save(appointmentDto);
            }
            return getResult(ResponseCode.SUCCESS_PROCESSED, appointmentDto);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    //学员签到
    @ApiOperation(value = "/singIn", notes = "签到")
    @PostMapping(value = "/singIn")
    public Result coachSingIn(@RequestBody SingInDto singInDto) {
        try {

            singInDto.setSinginId(UUID.randomUUID().toString());
            userAppointmentHistoryService.save(singInDto);

            return getResult(ResponseCode.SUCCESS_PROCESSED, singInDto);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch(DataAccessException e)
        {
            if(e.getMessage().contains("fk_appointment_id"))
            {
                return getResult(ResponseCode.DBERROR_INSERT_APPOINTMENTID_ForeignkeyError,singInDto);
            }
            else
            {
                return  getResult(ResponseCode.DBERROR_INSERT_ERROR,singInDto);
            }

        }

        catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //用户 根据时间得到预约
    @ApiOperation(value = "/getAppointmentAllByDate", notes = "根据时间得到预约")
    @RequestMapping("/getAppointmentAllByDate")
    public Result getAppointmentAllByDate(String userId,Date date)
    {
        try {
            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    userAppointmentService.list(new QueryWrapper<UserAppointment>()
                    .apply("TO_DAYS(appointment_time)-TO_DAYS({0}) = 0", date).eq("user_id",userId)));
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }

    //教练 根据时间得到预约的人数
    @ApiOperation(value = "/getAppointMembersByDates", notes = "根据时间得到预约")
    @RequestMapping("/getAppointMembersByDates")
    public Result getAppointMembersByDates(String coachId,Integer dateType,Integer customerType)
    {
        try {

            int weekDay = 0;
            int sevenDay = 7;
            int twoWeekDay = 14;
            int aragDays = 0;

            List<UserAppointmentHistory> appointments = new ArrayList<>();
            switch(dateType){
                case 0 :
                    Calendar calendar = Calendar.getInstance();
                    Date today = new Date();
                    calendar.setTime(today);// 此处可换为具体某一时间
                    weekDay = calendar.get(Calendar.DAY_OF_WEEK);
                    aragDays = weekDay;
                    break;
                case 1 :
                    aragDays = sevenDay;
                    break;
                case 2 :
                    aragDays = twoWeekDay;
            }

            // appointments = userAppointmentHistoryService.list(new QueryWrapper<UserAppointmentHistory>()
            //        .apply("TO_DAYS(singin_time)-TO_DAYS({0}) = 0", aragDays));
            List<UserInfo> userInfos = userAppointmentHistoryService.getAppointMembersByDates(aragDays,coachId,customerType);

            AppointmentMembersViewDto viewDto = new AppointmentMembersViewDto();
            viewDto.setTrainners(userInfos);
            viewDto.setTrainnerNums(userInfos.size());

            return getResult(ResponseCode.SUCCESS_PROCESSED,viewDto) ;

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }


}
