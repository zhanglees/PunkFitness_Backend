package com.healthclubs.pengke.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.*;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.*;
import com.healthclubs.pengke.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

            UserTrainPlanDto  userTrainPlanDto = new UserTrainPlanDto();

            if(trainPlainId == null || trainPlainId.isEmpty()){

                List<ClassInfo> classInfos = (List<ClassInfo>)this.getTrainClassByCoachId(coachId).getData();
                userTrainPlanDto.setClassInfos(classInfos);
                //return this.getTrainClassByCoachId(coachId);
            }
            else {

                List<ClassInfo> classInfos = classInfoService.list(new QueryWrapper<ClassInfo>()
                        .eq("owner_id", coachId).or().eq("owner_id", "system"));

                if (classInfos != null && classInfos.size() > 0) {

                    classInfos.stream().forEach(item -> {

                        UserTrainItem userTrainItem = userTrainItemService.getOne(new QueryWrapper<UserTrainItem>()
                                .eq("class_id", item.getClassId())
                                .eq("user_id", userId)
                                .eq("coach_id", coachId)
                                .eq("training_plan_id", trainPlainId).last("limit 1"));

                        if (userTrainItem != null) {
                            item.setUserTrainItem(userTrainItem);
                        }

                        List<ClassContent> classContents = classContentService.list(new QueryWrapper<ClassContent>()
                                .eq("train_class_id", item.getClassId())
                                .and(QueryWrapper->QueryWrapper.eq("owner",coachId).or()
                                        .eq("owner","system")));


                        if (classContents != null && classContents.size() > 0) {

                            classContents.stream().forEach(contentItem -> {
                                UserTrainplanClassContent userTrainplanClassContent = userTrainplanClassContentService.getOne(new QueryWrapper<UserTrainplanClassContent>()
                                        .eq("class_content_id", contentItem.getClassContentId())
                                        .eq("training_plan_id", trainPlainId)
                                        .eq("class_id", contentItem.getTrainClassId())
                                        .eq("user_id", userId)
                                        .eq("coach_id", coachId).last("limit 1"));

                                if (userTrainplanClassContent != null) {
                                    contentItem.setUserChose(true);
                                    contentItem.setItemValue(userTrainplanClassContent.contentItemValue);
                                }

                            });

                            item.setClassContents(classContents);
                        }
                    });
                }

                UserTrainingPlan userTrainingPlan = new UserTrainingPlan();
                userTrainingPlan = this.userTrainingPlanService.getById(trainPlainId);
                userTrainPlanDto.setClassInfos(classInfos);
                userTrainPlanDto.setUserTrainingPlan(userTrainingPlan);

               // return getResult(ResponseCode.SUCCESS_PROCESSED, classInfos);
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED, userTrainPlanDto);

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


    //修改训练课
    @ApiOperation(value = "/modifyUserTrainClass", notes = "修改训练课")
    @PostMapping(value = "/modifyUserTrainClass")
    public Result modifyUserTrainClass(@RequestBody  UserTrainItem userTrainItem)
    {
        try {

            if (userTrainItem==null || userTrainItem.getTrainingPlanId() == null
                    || userTrainItem.getTrainingPlanId().isEmpty()){

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userTrainItem);
            }

            String userTrainitemId = userTrainItem.getUserTrainitemId();
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
                this.userTrainItemService.updateById(userTrainItem);
            }

            List<UserTrainplanClassContent> currentContents =userTrainItem.getUserTrainplanClassContents();

            List<UserTrainplanClassContent> oldContents = this.userTrainplanClassContentService.list(new
                        QueryWrapper<UserTrainplanClassContent>().eq("user_id",userTrainItem.getUserId())
                .eq("coach_id",userTrainItem.getCoachId())
                .eq("training_plan_id",userTrainItem.getTrainingPlanId())
                .eq("class_id",userTrainItem.getClassId()));

            if(currentContents==null || currentContents.size()==0)
            {
                if(oldContents!=null && oldContents.size()>0)
                {
                    List<String> deltempids = oldContents.stream().map(UserTrainplanClassContent::getUserClasscontentId).collect(Collectors.toList());
                    userTrainplanClassContentService.remove(new QueryWrapper<UserTrainplanClassContent>().in(
                            "user_classcontent_id",deltempids
                    ));
                }
            }
            else
            {
                if(currentContents.size()>0)
                {
                    if(oldContents==null || oldContents.size()==0)
                    {
                        currentContents.stream().forEach(item->{
                            item.setUserClasscontentId(UUID.randomUUID().toString());
                            item.setCoachId(userTrainItem.getCoachId());
                            item.setUserId(userTrainItem.getUserId());
                        });
                        userTrainplanClassContentService.saveBatch(currentContents);
                    }
                    else
                    {
                        //比较不同 删除。 比较不同添加。
                        List<UserTrainplanClassContent> deletes = new ArrayList<>();
                        List<UserTrainplanClassContent> adds = new ArrayList<>();

                        List<String> contentIds = oldContents.stream().map(UserTrainplanClassContent::getClassContentId).collect(Collectors.toList());
                        List<String> contentOlds = currentContents.stream().map(UserTrainplanClassContent::getClassContentId).collect(Collectors.toList());

                        List<String> delIds = new ArrayList<>();
                        currentContents.stream().forEach(item->{
                            if(!contentIds.contains(item.getClassContentId()))
                            {
                                item.setUserClasscontentId(UUID.randomUUID().toString());
                                item.setUserId(userTrainItem.getUserId());
                                item.setCoachId(userTrainItem.getCoachId());

                                adds.add(item);
                            }
                        });

                        oldContents.stream().forEach(item->{
                            if(!contentOlds.contains(item.getClassContentId())){
                                deletes.add(item);
                                delIds.add(item.getUserClasscontentId());
                            }
                        });

                        if(deletes!=null && deletes.size()>0){
                            this.userTrainplanClassContentService.remove(new QueryWrapper<UserTrainplanClassContent>().in(
                                    "user_classcontent_id",delIds
                            ));
                        }

                        if(adds!=null && adds.size()>0){
                            this.userTrainplanClassContentService.saveBatch(adds);
                        }

                    }

                }
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED, userTrainItem);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE,e.getMessage());
        }
    }


    //修改训练计划
    @ApiOperation(value = "/modifyUserTrainPlan", notes = "修改训练计划")
    @PostMapping(value = "/modifyUserTrainPlan")
    public Result modifyUserTrainPlan(@RequestBody  UserTrainingPlan userTrainingPlan)
    {
        try {

            if (userTrainingPlan==null || userTrainingPlan.getTrainingPlanId() == null
                    || userTrainingPlan.getTrainingPlanId().isEmpty()){

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userTrainingPlan);
            }

            this.userTrainingPlanService.updateById(userTrainingPlan);

            return getResult(ResponseCode.SUCCESS_PROCESSED, userTrainingPlan);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //得到训练课的所有预约未签到的小课时
    @ApiOperation(value = "/getUserClassSection", notes = "得到训练课的所有预约未签到的小课时")
    @PostMapping("/getUserClassSection")
    public Result getUserClassSection(@RequestBody UserTrainItem userTrainItem)
    {
        try {

            if (userTrainItem==null || userTrainItem.getTrainingPlanId() == null
                    || userTrainItem.getTrainingPlanId().isEmpty()){

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userTrainItem);
            }

            List<UsertrainPlanSection> usertrainPlanSections = this.usertrainPlanSectionService.list(new
                    QueryWrapper<UsertrainPlanSection>().eq("user_id",userTrainItem.getUserId())
            .eq("coach_id",userTrainItem.getCoachId())
            .eq("user_trainitem_id",userTrainItem.getUserTrainitemId())
            .eq("training_plan_id",userTrainItem.getTrainingPlanId()));
            //.eq(""))

            return getResult(ResponseCode.SUCCESS_PROCESSED,usertrainPlanSections) ;

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }


    //编辑训练课下的训练课时
    @ApiOperation(value = "/getUserClassSectionDetail", notes = "编辑训练课下的训练课时")
    @PostMapping("/getUserClassSectionDetail")
    public Result getUserClassSectionDetail(@RequestBody UsertrainPlanSection usertrainPlanSection)
    {
        try {

            if (usertrainPlanSection==null || usertrainPlanSection.getUsertrainSectionId() == null
                    || usertrainPlanSection.getUsertrainSectionId().isEmpty()){

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, usertrainPlanSection);
            }

            List<UserTraionSectionDetail> userTraionSectionDetails = new ArrayList<>();

            userTraionSectionDetails = this.userTraionSectionDetailService.list(new
                    QueryWrapper<UserTraionSectionDetail>().eq("user_id",usertrainPlanSection.getUserId())
                    .eq("coach_id",usertrainPlanSection.getCoachId())
                    .eq("usertrain_section_id",usertrainPlanSection.getUsertrainSectionId()));
            usertrainPlanSection.setUserTraionSectionDetails(userTraionSectionDetails);

            return getResult(ResponseCode.SUCCESS_PROCESSED,usertrainPlanSection) ;

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }

    //删除训练课小节
    @ApiOperation(value = "/delUserSectionDetail", notes = "删除训练课小节")
    @PostMapping("/delUserSectionDetail")
    public Result delUserSectionDetail(@RequestBody UserTraionSectionDetail userTraionSectionDetail)
    {
        try {

            if (userTraionSectionDetail==null || userTraionSectionDetail.getSectionDetailId() == null
                    || userTraionSectionDetail.getSectionDetailId().isEmpty()
            ||userTraionSectionDetail.getUserId() == null || userTraionSectionDetail.getUserId().isEmpty()){
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userTraionSectionDetail);
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED,userTraionSectionDetailService.removeById(userTraionSectionDetail)) ;

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }

    //得到所有课程课时列表
    @ApiOperation(value = "/getUserSectionList", notes = "编辑训练课下的训练课时")
    @PostMapping("/getUserSectionList")
    public Result getUserSectionList(@RequestBody UsertrainPlanSection usertrainPlanSection)
    {
        try {

            if (usertrainPlanSection==null || usertrainPlanSection.getCoachId() == null
                    || usertrainPlanSection.getCoachId().isEmpty()
                    ||usertrainPlanSection.getUserId() == null || usertrainPlanSection.getUserId().isEmpty()){
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, usertrainPlanSection);
            }

            List<UsertrainPlanSection> usertrainPlanSections = new ArrayList<>();
            usertrainPlanSections = this.usertrainPlanSectionService.list(new QueryWrapper<UsertrainPlanSection>()
            .isNotNull("complete_time")
            .eq("user_id",usertrainPlanSection.getUserId())
            .eq("coach_id",usertrainPlanSection.getCoachId())
             .orderByDesc("create_time"));

            return getResult(ResponseCode.SUCCESS_PROCESSED,usertrainPlanSections) ;

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }

    //编辑训练课的小时课
    @ApiOperation(value = "/submitUserClassSection", notes = "编辑训练课的小时课")
    @PostMapping("/submitUserClassSection")
    public Result submitUserClassSection(@RequestBody UsertrainPlanSection usertrainPlanSection)
    {
        try {

            if (usertrainPlanSection==null || usertrainPlanSection.getUsertrainSectionId() == null
                    || usertrainPlanSection.getUsertrainSectionId().isEmpty()){

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, usertrainPlanSection);
            }
            //usertrainPlanSectionService.updateById(usertrainPlanSection);

            usertrainPlanSectionService.updateById(usertrainPlanSection);
            return getResult(ResponseCode.SUCCESS_PROCESSED,usertrainPlanSection);

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }

    //添加一个小节课下的小节细节
    @ApiOperation(value = "/adddUserSectionDetail", notes = "添加一个小节课下的小节细节")
    @PostMapping("/adddUserSectionDetail")
    public Result adddUserSectionDetail(@RequestBody UserTraionSectionDetail userTraionSectionDetail)
    {
        try {

            if (userTraionSectionDetail==null || userTraionSectionDetail.getUsertrainSectionId() == null
                    || userTraionSectionDetail.getUsertrainSectionId().isEmpty()){

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userTraionSectionDetail);
            }
            //usertrainPlanSectionService.updateById(usertrainPlanSection);
            userTraionSectionDetail.setSectionDetailId(UUID.randomUUID().toString());
            userTraionSectionDetailService.save(userTraionSectionDetail);

            return getResult(ResponseCode.SUCCESS_PROCESSED,userTraionSectionDetail);

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }

    //编辑训练课的小时课一个细节
    @ApiOperation(value = "/editUserClassSectionDetail", notes = "编辑训练课的小时课")
    @PostMapping("/editUserClassSectionDetail")
    public Result editUserClassSectionDetail(@RequestBody UserTraionSectionDetail userTraionSectionDetail)
    {
        try {

            if (userTraionSectionDetail==null || userTraionSectionDetail.getSectionDetailId() == null
                    || userTraionSectionDetail.getSectionDetailId().isEmpty()){

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userTraionSectionDetail);
            }
            //usertrainPlanSectionService.updateById(usertrainPlanSection);

            userTraionSectionDetailService.updateById(userTraionSectionDetail);

            return getResult(ResponseCode.SUCCESS_PROCESSED,userTraionSectionDetail) ;

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }

    //编辑训练课的小时课一个细节
    @ApiOperation(value = "/deleteUserClassSection", notes = "删除训练课小时课")
    @PostMapping("/deleteUserClassSection")
    public Result deleteUserClassSection(@RequestBody UsertrainPlanSection usertrainPlanSection)
    {
        try {

            if (usertrainPlanSection==null || usertrainPlanSection.getUsertrainSectionId() == null
                    || usertrainPlanSection.getUsertrainSectionId().isEmpty()){

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, usertrainPlanSection);
            }

            if(usertrainPlanSection.getCompleteTime()!=null)
            {
                return getResult(ResponseCode.TRAINCLASS_IS_START, usertrainPlanSection);
            }

            List<UserTraionSectionDetail> userTraionSectionDetails = userTraionSectionDetailService.list(
                    new QueryWrapper<UserTraionSectionDetail>()
                            .eq("usertrain_section_id",usertrainPlanSection.getUsertrainSectionId())
                            .eq("user_id",usertrainPlanSection.getUserId())
                            .eq("coach_id",usertrainPlanSection.getCoachId()));
            if(userTraionSectionDetails!=null && userTraionSectionDetails.size()>0)
            {
                List<String> ids = userTraionSectionDetails.stream().
                        map(UserTraionSectionDetail::getSectionDetailId).collect(Collectors.toList());
                userTraionSectionDetailService.removeByIds(ids);
            }
            return getResult(ResponseCode.SUCCESS_PROCESSED, usertrainPlanSectionService.removeById(usertrainPlanSection));

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }

    //复制训练小时课时
    @ApiOperation(value = "/copyUserClassSection", notes = "复制训练小时课时")
    @PostMapping("/copyUserClassSection")
    public Result copyUserClassSection(@RequestBody UsertrainPlanSection usertrainPlanSection)
    {
        try {

            if (usertrainPlanSection==null || usertrainPlanSection.getUsertrainSectionId() == null
                    || usertrainPlanSection.getUsertrainSectionId().isEmpty()){

                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, usertrainPlanSection);
            }

            List<UserTraionSectionDetail> userTraionSectionDetails = new ArrayList<>();

            userTraionSectionDetails = this.userTraionSectionDetailService.list(new
                    QueryWrapper<UserTraionSectionDetail>().eq("user_id",usertrainPlanSection.getUserId())
                    .eq("coach_id",usertrainPlanSection.getCoachId())
                    .eq("usertrain_section_id",usertrainPlanSection.getUsertrainSectionId()));

            usertrainPlanSection.setUsertrainSectionId(UUID.randomUUID().toString());
            if(userTraionSectionDetails!=null && userTraionSectionDetails.size()>0)
            {
                userTraionSectionDetails.stream().forEach(item->{
                    item.setSectionDetailId(UUID.randomUUID().toString());
                    item.setUsertrainSectionId(usertrainPlanSection.getUsertrainSectionId());
                });

            }


            usertrainPlanSection.setCreateTime(null);
            usertrainPlanSection.setCreateTime(new Date());
            usertrainPlanSection.setUserTraionSectionDetails(userTraionSectionDetails);

            //保存数据
            if(userTraionSectionDetails!=null && userTraionSectionDetails.size()>0)
            {
                userTraionSectionDetailService.saveBatch(userTraionSectionDetails);
            }
            usertrainPlanSectionService.save(usertrainPlanSection);


            return getResult(ResponseCode.SUCCESS_PROCESSED,usertrainPlanSection) ;

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }

}
