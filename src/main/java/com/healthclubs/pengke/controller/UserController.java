package com.healthclubs.pengke.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.UserInfo;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.MembersSearchDto;
import com.healthclubs.pengke.pojo.dto.RegisterDto;
import com.healthclubs.pengke.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api("User")
@RequestMapping("/api/user")
public class UserController extends BaseController {

    @Autowired
    IUserService userService;

    @GetMapping("/getUserAll")
    public Result getUserAll() {
        try {
            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    userService.getUserAll());
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }

    @GetMapping("/byid")
    public Result byid(String id) {
        try {
            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    userService.getById(id));
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //注册
    @ApiOperation(value = "/register", notes = "注册")
    @PostMapping(value = "/register")
    public Result register(@RequestBody RegisterDto userData) {
        try {

            if (userData.getTeacherId() == null || userData.getTeacherId().isEmpty()) {
                return getResult(ResponseCode.USER_COACHId_EMPTY, userData);
            } else {
                userData.setId(UUID.randomUUID().toString());
                userService.register(userData);
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED, userData);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //注册
    @ApiOperation(value = "/getTrainerInfoByCoachId", notes = "根据教练id得到学员id")
    @PostMapping(value = "/getTrainerInfoByCoachId")
    public Result getTrainerInfoByCoachId(String coachId, Integer trainerType) {
        try {

            if(trainerType==null){
              return getResult(ResponseCode.SUCCESS_PROCESSED,
                        userService.getAppointmentTrainers(coachId));
            }
            else
            {
                return getResult(ResponseCode.SUCCESS_PROCESSED,
                        userService.getTrainerInfoByCoachIdAndlevel(coachId, trainerType));
            }


        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //转成正式会员
    @ApiOperation(value = "/transformMember", notes = "转换正式会员")
    @PostMapping(value = "/transformMember")
    public Result transformMember(@RequestBody UserInfo userInfo) {
        try {
            Integer customeLevel = 1; //正式会员。
            userInfo.setCoustomLevel(customeLevel);

            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    userService.updateById(userInfo));

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //搜索会员
    @ApiOperation(value = "/searchMembers", notes = "搜索会员")
    @PostMapping(value = "/searchMembers")
    public Result searchMembers(@RequestBody MembersSearchDto membersSearchDto) {
        try {

            if (membersSearchDto == null || membersSearchDto.getCondition() == null||
                    membersSearchDto.getCondition().isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, membersSearchDto);
            }

            List<UserInfo> datas = new ArrayList<>();
            if(membersSearchDto.getTrainerType()==null){

                datas = userService.getAppointmentTrainers(membersSearchDto.getCoachId());
            }
            else {
                datas = userService.getTrainerInfoByCoachIdAndlevel(membersSearchDto.getCoachId(), membersSearchDto.getTrainerType());

            }

            datas = datas.stream().filter(item -> {
                        if (item.getUserName().contains(membersSearchDto.getCondition()) ||
                                item.getPhone().contains(membersSearchDto.getCondition())) {
                            return true;
                        } else {
                            return false;
                        }
                    }
            ).collect(Collectors.toList());

            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    datas);

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE, e.getMessage());
        }
    }



    //搜索会员
    @ApiOperation(value = "/modifyCoachInfoById", notes = "修改人员信息")
    @PostMapping(value = "/modifyCoachInfoById")
    public Result modifyUserInfoById(@RequestBody UserInfo userInfo) {
        try {

            if (userInfo == null || userInfo.getId()==null ||userInfo.getId().isEmpty()) {

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userInfo);
            }

            userService.updateById(userInfo);

            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    userInfo);

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE, e.getMessage());
        }
    }


}
