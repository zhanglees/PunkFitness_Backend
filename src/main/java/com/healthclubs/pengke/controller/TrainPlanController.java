package com.healthclubs.pengke.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.*;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.CoachTrainClassDto;
import com.healthclubs.pengke.pojo.dto.UserTrainClassListDto;
import com.healthclubs.pengke.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    IUsertrainPlanSectionService usertrainPlanSectionService;

    @Autowired
    IUserTraionSectionDetailService userTraionSectionDetailService;

    @Autowired
    private IUserhealthcheckReportService userhealthcheckReportService;

    //得到训练计划续联课内容
    @ApiOperation(value = "/getTrainClassByCoachId", notes = "得到教练下得课程")
    @GetMapping(value = "/getTrainClassByCoachId")
    public Result getTrainClassByCoachId(String coachId) {
        try {

            if (coachId == null || coachId.isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, coachId);
            }

            Integer isShow = 1; //可见
            List<ClassInfo> classInfos = classInfoService.list(new QueryWrapper<ClassInfo>()
                    .eq("is_show",isShow)
                    .eq("owner_id", coachId).or().eq("owner_id", "system"));

            if (classInfos != null && classInfos.size() > 0) {

                classInfos.stream().forEach(item -> {
                    List<ClassContent> classContents = classContentService.list(new QueryWrapper<ClassContent>()

                            .eq("owner", coachId).or().eq("owner", "system")
                            .eq("train_class_id", item.getClassId()));
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

            Integer isShow = 1;//课程显示
            classInfo.setIsShow(isShow);

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

    //创建训练计划
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

            Date createTime = new Date();

            String userTrainingPlanid = UUID.randomUUID().toString();
            String userId = userTrainingPlan.getUserId();
            String coachId = userTrainingPlan.getCoachId();

            userTrainingPlan.setTrainingPlanId(userTrainingPlanid);
            userTrainingPlan.setCreateTime(createTime);
            userTrainingPlanService.save(userTrainingPlan);


           List<UserTrainItem>  userTrainItems = userTrainingPlan.getUserTrainItems();

           if(userTrainItems!=null && userTrainItems.size()>0)
           {
               userTrainItems.stream().forEach(item->{
                   item.setUserTrainitemId(UUID.randomUUID().toString());
                   //设置训练计划id
                   item.setTrainingPlanId(userTrainingPlanid);
                   item.setUserId(userId);
                   item.setCoachId(coachId);
                   //设置时间
                   item.setCreateTime(createTime);

                   List<UserTrainplanClassContent> userTrainplanClassContents = item.getUserTrainplanClassContents();

                   if(userTrainplanClassContents!=null&&userTrainplanClassContents.size()>0)
                   {
                       userTrainplanClassContents.stream().forEach(childitem->
                       {
                           childitem.setUserClasscontentId(UUID.randomUUID().toString());
                           childitem.setTrainingPlanId(userTrainingPlanid);
                           childitem.setUserId(userId);
                           childitem.setCoachId(coachId);
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

    //创建训练课小节规划
    @ApiOperation(value = "/addUserTrainClassSection", notes = "创建训练课小节规划")
    @PostMapping(value = "/addUserTrainClassSection")
    public Result AddUserTrainClassSection(@RequestBody UsertrainPlanSection usertrainPlanSection)
    {
        try {

            if (usertrainPlanSection == null || usertrainPlanSection.getCoachId()== null ||
                    usertrainPlanSection.getCoachId().isEmpty() || usertrainPlanSection.getUserId() == null ||
                    usertrainPlanSection.getUserId().isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, usertrainPlanSection);
            }

            String userTrainingPlanSectionId = UUID.randomUUID().toString();
            String userId = usertrainPlanSection.getUserId();
            String coachId = usertrainPlanSection.getCoachId();

            usertrainPlanSection.setUsertrainSectionId(userTrainingPlanSectionId);
            usertrainPlanSection.setCreateTime(new Date());
            usertrainPlanSectionService.save(usertrainPlanSection);

            List<UserTraionSectionDetail> details = usertrainPlanSection.getUserTraionSectionDetails();

            if(details!=null && details.size()>0){
                details.stream().forEach(item->{
                    item.setSectionDetailId(UUID.randomUUID().toString());
                    item.setUsertrainSectionId(userTrainingPlanSectionId);
                    item.setUserId(userId);
                    item.setCoachId(coachId);
                });
            }
            userTraionSectionDetailService.saveBatch(details);

            return getResult(ResponseCode.SUCCESS_PROCESSED, usertrainPlanSection);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    //删除教练训练课
    @ApiOperation(value = "/deleteCoachTrainClass", notes = "删除教练训练课")
    @PostMapping(value = "/deleteCoachTrainClass")
    public Result DeleteCoachTrainClass(@RequestBody CoachTrainClassDto coachTrainClassDto)
    {
        try {

            if (coachTrainClassDto == null || coachTrainClassDto.getCoachId()== null ||
                    coachTrainClassDto.getCoachId().isEmpty() || coachTrainClassDto.getClassId() == null ||
                    coachTrainClassDto.getClassId().isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, coachTrainClassDto);
            }

            String userTrainingPlanSectionId = UUID.randomUUID().toString();
            String classId = coachTrainClassDto.getClassId();
            String coachId = coachTrainClassDto.getCoachId();


            ClassInfo classInfo = this.classInfoService.getById(classId);

            if(classInfo.getOwnerId().equals(coachId) && classId!="system"){
                Integer isShow = 0; //不可见
                classInfo.setIsShow(isShow);
                this.classInfoService.updateById(classInfo);
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED, classInfo);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    //得到训练课列表
    @ApiOperation(value = "/getUserClassByCoachId", notes = "得到用户的训练课列表")
    @GetMapping(value = "/getUserClassByCoachId")
    public Result GetUserClassByCoachId(String userId,String coachId)
    {
        try {

            if (userId == null || coachId== null ||
                    userId.isEmpty() || coachId.isEmpty()) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userId+coachId);
            }

            UserTrainClassListDto userTrainClassListDto = new UserTrainClassListDto();

            List<UserTrainItem> userTrainItems = this.userTrainItemService.getUserClassByCoachId(userId,coachId);
            if(userTrainItems!=null && userTrainItems.size()>0)
            {
                userTrainClassListDto.setUserTrainItems(userTrainItems);
                userTrainClassListDto.setTrainingPlanId(userTrainItems.get(0).getTrainingPlanId());
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED, userTrainClassListDto);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    //删除用户的训练课
    @ApiOperation(value = "/delUserTrainClassById", notes = "删除用户的训练课")
    @GetMapping(value = "/delUserTrainClassById")
    public Result delUserTrainClassById(String userTrainitemId)
    {
        try {
            if (userTrainitemId == null ||userTrainitemId.isEmpty()) {

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userTrainitemId);
            }
            List<UsertrainPlanSection> usertrainPlanSections = this.usertrainPlanSectionService.list(
                    new QueryWrapper<UsertrainPlanSection>()
                    .eq("user_trainitem_id",userTrainitemId)
                    .isNotNull("complete_time"));
            if(usertrainPlanSections!=null && usertrainPlanSections.size()>0)
            {
                return getResult(ResponseCode.TRAINCLASS_IS_START, userTrainitemId);
            }
            else
            {
                List<UsertrainPlanSection> datas = this.usertrainPlanSectionService.list(
                        new QueryWrapper<UsertrainPlanSection>().eq("user_trainitem_id",userTrainitemId));

                List<String> usertrainPlanSectionIds = datas.stream().map(UsertrainPlanSection::getUsertrainSectionId)
                        .collect(Collectors.toList());

                if(usertrainPlanSectionIds==null || usertrainPlanSectionIds.size()==0)
                {
                    this.userTrainItemService.removeById(userTrainitemId);
                    return getResult(ResponseCode.SUCCESS_PROCESSED, userTrainitemId);
                }

                List<UserTraionSectionDetail> userTraionSectionDetails = this.userTraionSectionDetailService.list(
                        new QueryWrapper<UserTraionSectionDetail>().in("usertrain_section_id",usertrainPlanSectionIds)
                );

                if(userTraionSectionDetails!=null && userTraionSectionDetails.size()>0)
                {
                    this.userTraionSectionDetailService.removeByIds(userTraionSectionDetails);
                }

                this.usertrainPlanSectionService.removeByIds(usertrainPlanSectionIds);
                this.userTrainItemService.removeById(userTrainitemId);
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED, userTrainitemId);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //得到训练课内容细节
    @ApiOperation(value = "/getTrainClassItemContent", notes = "删除用户的训练课")
    @PostMapping(value = "/getTrainClassItemContent")
    public Result getTrainClassItemContent(@RequestBody UserTrainItem userTrainItem)
    {
        try {

            List<UserTrainplanClassContent> userTrainplanClassContents =
                    userTrainplanClassContentService.getTrainClassItemContent(userTrainItem.getClassId(),
                            userTrainItem.getTrainingPlanId(),userTrainItem.getUserId(),userTrainItem.getCoachId());

            userTrainItem.setUserTrainplanClassContents(userTrainplanClassContents);

            return getResult(ResponseCode.SUCCESS_PROCESSED, userTrainItem);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //得到训练课细节
    @ApiOperation(value = "/getUserTrainPlainDetai", notes = "得到训练课细节")
    @GetMapping(value = "/getUserTrainPlainDetai")
    public Result getUserTrainPlainDetail(String trainPlainId,String userId,String coachId)
    {
        try {

            if (userId==null || userId.isEmpty() || coachId == null || coachId.isEmpty()){

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, trainPlainId+":"+userId+":"+coachId);
            }

            if(trainPlainId == null || trainPlainId.isEmpty()){
                return this.getTrainClassByCoachId(coachId);
            }
            else {

                List<ClassInfo> classInfos = classInfoService.list(new QueryWrapper<ClassInfo>()
                        .eq("owner_id", coachId).or().eq("owner_id", "system"));

                if (classInfos != null && classInfos.size() > 0) {

                    classInfos.stream().forEach(item -> {

                        UserTrainItem userTrainItem = userTrainItemService.getOne(new QueryWrapper<UserTrainItem>()
                                .eq("class_id",item.getClassId())
                                .eq("user_id",userId)
                                .eq("coach_id",coachId)
                                .eq("training_plan_id",trainPlainId).last("limit 1"));

                        if(userTrainItem!=null)
                        {
                            item.setUserTrainItem(userTrainItem);
                        }

                        List<ClassContent> classContents = classContentService.list(new QueryWrapper<ClassContent>()

                                .eq("owner", coachId).or().eq("owner", "system")
                                .eq("train_class_id", item.getClassId()));

                        if (classContents != null && classContents.size() > 0) {

                            classContents.stream().forEach(contentItem->{
                                UserTrainplanClassContent userTrainplanClassContent = userTrainplanClassContentService.getOne(new QueryWrapper<UserTrainplanClassContent>()
                                .eq("class_content_id",contentItem.getClassContentId())
                                .eq("training_plan_id",trainPlainId)
                                .eq("user_id",userId)
                                .eq("coach_id",coachId).last("limit 1"));

                                if(userTrainplanClassContent!=null)
                                {
                                    contentItem.setUserChose(true);
                                    contentItem.setItemValue(userTrainplanClassContent.contentItemValue);
                                }

                            });

                            item.setClassContents(classContents);
                        }
                    });
                }

                return getResult(ResponseCode.SUCCESS_PROCESSED, classInfos);
            }


        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }



    //创建用户训练课
    @ApiOperation(value = "/createUserTrainClass", notes = "创建用户训练课")
    @PostMapping(value = "/createUserTrainClass")
    public Result createUserTrainClass(@RequestBody  UserTrainClassListDto userTrainClassListDto)
    {
        try {

            if (userTrainClassListDto==null || userTrainClassListDto.getTrainingPlanId() == null
                    || userTrainClassListDto.getTrainingPlanId().isEmpty()){

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userTrainClassListDto);
            }

            String userTrainingPlanid = userTrainClassListDto.getTrainingPlanId();

            if(userTrainClassListDto.getUserTrainItems()!=null && userTrainClassListDto.getUserTrainItems().size()>0){
                userTrainClassListDto.getUserTrainItems().stream().forEach(item->{
                    item.setUserTrainitemId(UUID.randomUUID().toString());
                    item.setCreateTime(new Date());
                    item.setTrainingPlanId(userTrainingPlanid);

                    item.setUserId(userTrainClassListDto.getUserId());
                    item.setCoachId(userTrainClassListDto.getCoachId());

                    List<UserTrainplanClassContent> userTrainplanClassContents = item.getUserTrainplanClassContents();

                    if(userTrainplanClassContents!=null&&userTrainplanClassContents.size()>0)
                    {
                        userTrainplanClassContents.stream().forEach(childitem->
                        {
                            childitem.setUserClasscontentId(UUID.randomUUID().toString());
                            childitem.setTrainingPlanId(userTrainingPlanid);
                            childitem.setUserId(userTrainClassListDto.getUserId());
                            childitem.setCoachId(userTrainClassListDto.getCoachId());
                        });

                        //设置训练计划id
                        userTrainplanClassContentService.saveBatch(userTrainplanClassContents);
                    }

                });

                userTrainItemService.saveBatch(userTrainClassListDto.getUserTrainItems());
            }


            return getResult(ResponseCode.SUCCESS_PROCESSED, userTrainClassListDto);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }



}
