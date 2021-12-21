package com.healthclubs.pengke.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.*;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.AppointmentDto;
import com.healthclubs.pengke.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@Api("trainPlan")
@RequestMapping("/api/trainPlan")
public class TrainPlanController extends BaseController {


    @Autowired
    IClassInfoService classInfoService;

    @Autowired
    IClassContentService classContentService;

    @Autowired
    IUserTrainingPlanService userTrainingPlanService;

    @Autowired
    IUserTrainItemService userTrainItemService;

    @Autowired
    IUserTrainplanClassContentService userTrainplanClassContentService;


    //得到训练计划续联课内容
    @ApiOperation(value = "/getTrainClassByCoachId", notes = "得到教练下得课程")
    @GetMapping(value = "/getTrainClassByCoachId")
    public Result getTrainClassByCoachId(String coachId) {
        try {

            if (coachId == null || coachId.isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, coachId);
            }

            List<ClassInfo> classInfos = classInfoService.list(new QueryWrapper<ClassInfo>()
                    .eq("owner_id", coachId).or().eq("owner_id", "system"));

            if (classInfos != null && classInfos.size() > 0) {

                classInfos.stream().forEach(item -> {
                    List<ClassContent> classContents = classContentService.list(new QueryWrapper<ClassContent>()
                            .eq("train_class_id", item.getClassId())
                            .eq("owner", coachId).or().eq("owner", "system"));
                    if (classContents != null && classContents.size() > 0) {
                        item.setClassContents(classContents);
                    }
                });
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED, classInfos);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    //创建训练课
    @ApiOperation(value = "/addTrainClass", notes = "添加课程")
    @PostMapping(value = "/addTrainClass")
    public Result AddTrainClass(@RequestBody ClassInfo classInfo)
    {
        try {

            if (classInfo == null || classInfo.getOwnerId()== null || classInfo.getOwnerId().isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, classInfo);
            }

            classInfo.setClassId(UUID.randomUUID().toString());
            classInfo.setCreateTime(new Date());

            classInfoService.save(classInfo);

            List<ClassContent> classContents = classContentService.list(new QueryWrapper<ClassContent>()
                    .eq("train_class_id", "4ff671ed-b34f-103a-93cd-9854d842c0fa")
                    .eq("owner", "system"));

            if(classContents!=null && classContents.size()>0){
                classContents.stream().forEach(item->{
                    item.setClassContentId(UUID.randomUUID().toString());
                    item.setTrainClassId(classInfo.getClassId());
                    item.setOwner(classInfo.getOwnerId());
                });
            }

            classContentService.saveBatch(classContents);
            classInfo.setClassContents(classContents);

            return getResult(ResponseCode.SUCCESS_PROCESSED, classInfo);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //创建训练课标签
    @ApiOperation(value = "/addTrainClassContent", notes = "创建训练课内容")
    @PostMapping(value = "/addTrainClassContent")
    public Result AddTrainClassContent(@RequestBody ClassContent classContent)
    {
        try {

            if (classContent == null || classContent.getOwner()== null || classContent.getOwner().isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, classContent);
            }

            classContent.setClassContentId(UUID.randomUUID().toString());

            classContentService.save(classContent);

            return getResult(ResponseCode.SUCCESS_PROCESSED, classContent);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //创建训练课标签
    @ApiOperation(value = "/createUserTrainPlan", notes = "创建训练计划")
    @PostMapping(value = "/createUserTrainPlan")
    public Result CreateUserTrainPlan(@RequestBody UserTrainingPlan userTrainingPlan)
    {
        try {

            if (userTrainingPlan == null || userTrainingPlan.getCoachId()== null ||
                    userTrainingPlan.getCoachId().isEmpty() || userTrainingPlan.getUserId() == null ||
            userTrainingPlan.getUserId().isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userTrainingPlan);
            }

            String userTrainingPlanid = UUID.randomUUID().toString();
            userTrainingPlan.setTrainingPlanId(userTrainingPlanid);
            userTrainingPlanService.save(userTrainingPlan);

           List<UserTrainItem>  userTrainItems = userTrainingPlan.getUserTrainItems();

           if(userTrainItems!=null && userTrainItems.size()>0)
           {
               userTrainItems.stream().forEach(item->{
                   item.setUserTrainitemId(UUID.randomUUID().toString());
                   //设置训练计划id
                   item.setTrainingPlanId(userTrainingPlanid);

                   List<UserTrainplanClassContent> userTrainplanClassContents = item.getUserTrainplanClassContents();

                   if(userTrainplanClassContents!=null&&userTrainplanClassContents.size()>0)
                   {
                       userTrainplanClassContents.stream().forEach(childitem->
                       {
                           childitem.setUserClasscontentId(UUID.randomUUID().toString());
                           childitem.setTrainingPlanId(userTrainingPlanid);
                       });

                       //设置训练计划id
                       userTrainplanClassContentService.saveBatch(userTrainplanClassContents);
                   }
               });

               userTrainItemService.saveBatch(userTrainItems);
           }

            return getResult(ResponseCode.SUCCESS_PROCESSED, userTrainingPlan);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }



}
