package com.healthclubs.pengke.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.UserhealthcheckReport;
import com.healthclubs.pengke.entity.UserhealthcheckResource;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.UserHealthCheckDto;
import com.healthclubs.pengke.service.IResourceInfoService;
import com.healthclubs.pengke.service.IUserService;
import com.healthclubs.pengke.service.IUserhealthcheckReportService;
import com.healthclubs.pengke.service.IUserhealthcheckResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Api("healthCheck")
@RequestMapping("/api/healthCheck")
public class HealthCheckController extends BaseController {


    @Autowired
    private IUserhealthcheckReportService userhealthcheckReportService;

    @Autowired
    IUserService userService;

    @Autowired
    IUserhealthcheckResourceService userhealthcheckResourceService;

    @Autowired
    IResourceInfoService resourceInfoService;

    //得到用户所有的体检
    @RequestMapping("/getUserHealthCheckAll")
    public Result getUserHealthCheckAll(String coachId, String userId) {
        try {

            if (coachId == null || coachId.isEmpty() || userId == null || userId.isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, coachId);
            }

            List<UserhealthcheckReport> userhealthcheckReports = new ArrayList<>();

            userhealthcheckReports = userhealthcheckReportService.list(new QueryWrapper<UserhealthcheckReport>()
                    .eq("user_id", userId));
            if (userhealthcheckReports != null && userhealthcheckReports.size() > 0) {
                userhealthcheckReports.stream().forEach(item -> {

                    String coachName = userService.getById(item.getCoachId()).getUserName();
                    item.setCoachName(coachName);
                });
            }


            return getResult(ResponseCode.SUCCESS_PROCESSED, userhealthcheckReports
            );
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

            if (userHealthCheckDto == null || userHealthCheckDto.getUserhealthcheckReport() == null) {

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userHealthCheckDto);
            }

            userHealthCheckDto.getUserhealthcheckReport().setUserHealthcheckId(UUID.randomUUID().toString());
            userhealthcheckReportService.save(userHealthCheckDto.getUserhealthcheckReport());
            //保存资源。
            UserhealthcheckResource userhealthcheckResource = new UserhealthcheckResource();
            userhealthcheckResource = userHealthCheckDto.getUserhealthcheckResource();

            userhealthcheckResource.setUserId(userHealthCheckDto.getUserhealthcheckReport().getUserId());
            userhealthcheckResource.setUserHealthcheckrecourceId(UUID.randomUUID().toString());
            userhealthcheckResource.setUserHealthcheckId(userHealthCheckDto.getUserhealthcheckReport().getUserHealthcheckId());


            return getResult(ResponseCode.SUCCESS_PROCESSED, userhealthcheckResourceService.save(userhealthcheckResource));
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

            UserHealthCheckDto userHealthCheckDto = new UserHealthCheckDto();
            UserhealthcheckReport userhealthcheckReport = new UserhealthcheckReport();
            userhealthcheckReport = userhealthcheckReportService.getById(reportId);

            UserhealthcheckResource userhealthcheckResource = new UserhealthcheckResource();

            if(userhealthcheckReport!=null)
            {
                String coachName = userService.getById(userhealthcheckReport.getCoachId()).getUserName();
                userhealthcheckReport.setCoachName(coachName);
            }

            List<UserhealthcheckResource> userhealthcheckResources =
                    userhealthcheckResourceService.list(new QueryWrapper<UserhealthcheckResource>()
                            .eq("user_id", userhealthcheckReport.getUserId())
                            .eq("user_healthcheck_id", userhealthcheckReport.getUserHealthcheckId()));

            if (userhealthcheckResources != null && userhealthcheckResources.size() > 0) {
                userhealthcheckResource = userhealthcheckResources.get(0);

                if(resourceInfoService.getById(userhealthcheckResource.getItemRightPath())!=null)
                {
                    String itemRight =resourceInfoService.getById(userhealthcheckResource.getItemRightPath()).getResourcePath();
                    userhealthcheckResource.setItemRightPath(itemRight);
                }

                if(resourceInfoService.getById(userhealthcheckResource.getItemLeftPath())!=null)
                {
                    String itemLeft = resourceInfoService.getById(userhealthcheckResource.getItemLeftPath()).getResourcePath();
                    userhealthcheckResource.setItemLeftPath(itemLeft);
                }

                if(resourceInfoService.getById(userhealthcheckResource.getItemAheadPath())!=null)
                {
                    String itemAhead = resourceInfoService.getById(userhealthcheckResource.getItemAheadPath()).getResourcePath();
                    userhealthcheckResource.setItemAheadPath(itemAhead);
                }

                if(resourceInfoService.getById(userhealthcheckResource.getItemBackPath())!=null)
                {
                    String itemBack = resourceInfoService.getById(userhealthcheckResource.getItemBackPath()).getResourcePath();
                    userhealthcheckResource.setItemBackPath(itemBack);
                }



                if(resourceInfoService.getById(userhealthcheckResource.getHealthReportPath())!=null)
                {
                    String reportUrl = resourceInfoService.getById(userhealthcheckResource.getHealthReportPath()).getResourcePath();
                    userhealthcheckResource.setHealthReportPath(reportUrl);
                }


                userHealthCheckDto.setUserhealthcheckResource(userhealthcheckResource);
            }

            userHealthCheckDto.setUserhealthcheckReport(userhealthcheckReport);


            return getResult(ResponseCode.SUCCESS_PROCESSED,userHealthCheckDto
                    );
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

}
