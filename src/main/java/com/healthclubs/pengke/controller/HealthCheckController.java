package com.healthclubs.pengke.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.UserhealthcheckReport;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.UserHealthCheckDto;
import com.healthclubs.pengke.service.IUserhealthcheckReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api("healthCheck")
@RequestMapping("/api/healthCheck")
public class HealthCheckController extends BaseController{


    @Autowired
    private IUserhealthcheckReportService userhealthcheckReportService;

    //得到用户所有的体检
    @RequestMapping("/getUserHealthCheckAll")
    public Result getUserHealthCheckAll(String coachId,String userId) {
        try {

            if (coachId == null || coachId.isEmpty() || userId == null || userId.isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, coachId);
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    userhealthcheckReportService.list(new QueryWrapper<UserhealthcheckReport>().eq("user_id",userId)
                            .eq("coach_id",coachId)));
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }


    //添加用户体检报告
    @ApiOperation(value = "/addHealthCheckReport", notes = "添加用户体检报告")
    @PostMapping(value = "/addHealthCheckReport")
    public Result addHealthCheckReport(@RequestBody UserHealthCheckDto userHealthCheckDto) {
        try {

            if (userHealthCheckDto == null || userHealthCheckDto.getUserhealthcheckReport()==null) {

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userHealthCheckDto);
            }

            userHealthCheckDto.getUserhealthcheckReport().setUserHealthcheckId(UUID.randomUUID().toString());
            return getResult(ResponseCode.SUCCESS_PROCESSED, userhealthcheckReportService.save(userHealthCheckDto.getUserhealthcheckReport()));
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    //查看某次报告
    @ApiOperation(value = "/getHealthReportDetail", notes = "得到某一次的详细")
    @PostMapping(value = "/getHealthReportDetail")
    public Result getHealthReportDetail(String reportId) {
        try {

            if (reportId == null || reportId.isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, reportId);
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    userhealthcheckReportService.getById(reportId));
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

}
