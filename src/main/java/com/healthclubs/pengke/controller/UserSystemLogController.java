package com.healthclubs.pengke.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.UserSystemLog;
import com.healthclubs.pengke.entity.UserTrainItem;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.UserTrainClassListDto;
import com.healthclubs.pengke.service.IUserSystemLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@Api("UserSystemLog")
@RequestMapping("/api/userSystemLog")
public class UserSystemLogController extends BaseController{


    @Autowired
    IUserSystemLogService userSystemLogService;

    //得到用户操作日志
    @ApiOperation(value = "/getUserLogById", notes = "得到用户操作日志")
    @GetMapping(value = "/getUserLogById")
    public Result getUserLogById(String userId)
    {
        try {

            if (userId == null || userId.isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userId);
            }

            List<UserSystemLog> userSystemLogs = this.userSystemLogService.list(new QueryWrapper<UserSystemLog>()
            .eq("user_id",userId));


            return getResult(ResponseCode.SUCCESS_PROCESSED, userSystemLogs);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


}
