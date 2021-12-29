package com.healthclubs.pengke.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.UserTraionSectionDetail;
import com.healthclubs.pengke.entity.UsertrainPlanSection;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.service.IUserTrainItemService;
import com.healthclubs.pengke.service.IUserTraionSectionDetailService;
import com.healthclubs.pengke.service.IUsertrainPlanSectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@Api("experienceLesson")
@RequestMapping("/api/experienceLesson")
public class ExperienceLessonController extends BaseController{

    private static final String trainItemId = "cc7cfd9a-b904-103a-9f13-c5d2cd8b50f0";
    private static final String exprienceClassPlan = "exprienceClassPlan";
    private static final String sectionName = "体验课";

    @Autowired
    IUserTraionSectionDetailService userTraionSectionDetailService;

    @Autowired
    IUsertrainPlanSectionService usertrainPlanSectionService;

    @Autowired
    IUserTrainItemService userTrainItemService;


    //得到用户体验课
    @ApiOperation(value = "/getUserExperienceLessonDetail", notes = "得到体验课细节")
    @GetMapping(value = "/getUserExperienceLessonDetail")
    public Result getUserExperienceLessonDetail(String userId, String coachId)
    {

        if (userId==null || userId.isEmpty() ||coachId ==null
                || coachId.isEmpty()){
            return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userId+coachId);
        }

       // UserTrainItem userTrainItem = userTrainItemService.getOne(new QueryWrapper<UserTrainItem>()
       // .eq("user_id",userId).eq("coach_id",coachId)
       // .eq("class_id",exprienceLessonId).last("limit 1"));
        UsertrainPlanSection usertrainPlanSection = new UsertrainPlanSection();
        usertrainPlanSection = usertrainPlanSectionService.getOne(new QueryWrapper<UsertrainPlanSection>()
        .eq("user_id",userId)
        .eq("coach_id",coachId)
        .eq("training_plan_id",exprienceClassPlan).last("limit 1"));

        if(usertrainPlanSection == null){
            //创建
            Integer showOrder = 1;
            usertrainPlanSection = new UsertrainPlanSection();
            usertrainPlanSection.setCreateTime(new Date());
            usertrainPlanSection.setShowOrder(showOrder);
            usertrainPlanSection.setUserId(userId);
            usertrainPlanSection.setCoachId(coachId);
            usertrainPlanSection.setUsertrainSectionId(UUID.randomUUID().toString());
            usertrainPlanSection.setUserTrainitemId(trainItemId);
            usertrainPlanSection.setTrainingPlanId(exprienceClassPlan);
            usertrainPlanSection.setSectionName(sectionName);
            usertrainPlanSectionService.save(usertrainPlanSection);
        }
        else
        {
            List<UserTraionSectionDetail> userTraionSectionDetails = userTraionSectionDetailService.list(
                    new QueryWrapper<UserTraionSectionDetail>().eq("user_id",userId)
            .eq("coach_id",coachId)
            .eq("usertrain_section_id",usertrainPlanSection.getUsertrainSectionId()));

            if(userTraionSectionDetails!=null && userTraionSectionDetails.size()>0){
                usertrainPlanSection.setUserTraionSectionDetails(userTraionSectionDetails);
            }
        }

        try {
            return getResult(ResponseCode.SUCCESS_PROCESSED, usertrainPlanSection);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

}
