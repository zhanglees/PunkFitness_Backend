package com.healthclubs.pengke.controller;

import com.healthclubs.pengke.entity.UserInfo;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.RegisterDto;
import com.healthclubs.pengke.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Api("User")
@RequestMapping("/api/user")
public class UserController extends BaseController{

    @Autowired
    IUserService userService;

    @RequestMapping("getUserAll")
    @ResponseBody
    public List<UserInfo> getUserAll()
    {
         return this.userService.getUserAll();
    }

    @RequestMapping("/byid")
    public Object byid(String id) {
        Object t = userService.getById(id);
        //  return userService.getById(id);
        return t;
    }


    //注册
    @ApiOperation(value = "/register", notes = "注册")
    @PostMapping(value = "/register")
    public Result register(@RequestBody RegisterDto userData) {
        try {

            if(userData.getTeacherId()==null || userData.getTeacherId().isEmpty())
            {
                return getResult(ResponseCode.USER_COACHId_EMPTY,userData);
            }
            else
            {
                userData.setId(UUID.randomUUID().toString());
                userService.register(userData);
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED,userData);
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
    public  Result getTrainerInfoByCoachId(String coachId)
    {
        try {
            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    userService.getTrainerInfoByCoachId(coachId));
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }




}
