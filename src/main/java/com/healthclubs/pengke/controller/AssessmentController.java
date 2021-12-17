package com.healthclubs.pengke.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.*;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.UserAssessmentDto;
import com.healthclubs.pengke.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Api("assessment")
@RequestMapping("/api/assessment")
public class AssessmentController extends BaseController {

    @Autowired
    private IAssessmentContentService assessmentContentService;

    @Autowired
    private IAssessmentFeedbacksService assessmentFeedbacksService;

    @Autowired
    private IUserAssessmentFeedbackService userAssessmentFeedbackService;

    @Autowired
    private IUserAssessmentListViewService userAssessmentListViewService;

    @Autowired
    private IUserAssessmentResourceService userAssessmentResourceService;

    @Autowired
    private IResourceInfoService resourceInfoService;

    //根据教练id得到评估测试内容
    @RequestMapping("/getAssessmentByCoachId")
    public Result getAssessmentByCoachId(String coachId) {
        try {

            if (coachId == null || coachId.isEmpty()) {
                return getResult(ResponseCode.USER_COACHId_EMPTY, coachId);
            }

            List<AssessmentContent> assessmentContents = assessmentContentService.list();

            if (assessmentContents != null && assessmentContents.size() > 0) {
                assessmentContents.stream().forEach(item -> {
                    List<AssessmentFeedbacks> assessmentFeedbacks = assessmentFeedbacksService.list(new
                            QueryWrapper<AssessmentFeedbacks>().eq(
                            "owner", coachId).or().eq("owner", "system").eq(
                            "assessment_id", item.assessmentId)
                    );

                    if (assessmentFeedbacks != null && assessmentFeedbacks.size() > 0) {
                        assessmentFeedbacks.stream().forEach(childItem -> {

                            List<AssessmentFeedbacks> childFeedbacks = assessmentFeedbacksService.list(
                                    new QueryWrapper<AssessmentFeedbacks>().eq("parent_id", childItem.assessmentFeedbackId));
                            childItem.setChildFeedbacks(childFeedbacks);

                        });
                    }

                    item.setFeedbacks(assessmentFeedbacks);
                });
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    assessmentContents);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }

    //根据教练id,评估类型得到内容
    @RequestMapping("/getAssessmentByType")
    public Result getAssessmentByType(String coachId, Integer assessmentType) {
        try {

            if (coachId == null || coachId.isEmpty()) {
                return getResult(ResponseCode.USER_COACHId_EMPTY, coachId);
            }

            List<AssessmentContent> assessmentContents = assessmentContentService.list(
                    new QueryWrapper<AssessmentContent>()
                            .eq("assessment_type", assessmentType)
                            .orderByAsc("show_order")
            );

            if (assessmentContents != null && assessmentContents.size() > 0) {
                assessmentContents.stream().forEach(item -> {
                    List<AssessmentFeedbacks> assessmentFeedbacks = assessmentFeedbacksService.list(new
                            QueryWrapper<AssessmentFeedbacks>().eq(
                            "owner", coachId).or().eq("owner", "system").eq(
                            "assessment_id", item.assessmentId)
                           .isNull("parent_id").orderByAsc("show_Order")
                    );

                    if (assessmentFeedbacks != null && assessmentFeedbacks.size() > 0) {
                        assessmentFeedbacks.stream().forEach(childItem -> {

                            List<AssessmentFeedbacks> childFeedbacks = assessmentFeedbacksService.list(
                                    new QueryWrapper<AssessmentFeedbacks>().eq("parent_id", childItem.assessmentFeedbackId));
                            childItem.setChildFeedbacks(childFeedbacks);

                        });
                    }

                    item.setFeedbacks(assessmentFeedbacks);
                });
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    assessmentContents);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }

    }


    //创建教练的反馈标签
    @ApiOperation(value = "/createNewFeedbacks", notes = "创建反馈标签")
    @PostMapping(value = "/createNewFeedbacks")
    public Result createNewFeedbacks(@RequestBody AssessmentFeedbacks assessmentFeedbacks) {
        try {

            if (assessmentFeedbacks == null || assessmentFeedbacks.getAssessmentId().isEmpty()) {
                return getResult(ResponseCode.FEEDBACK_ASSESSMENTID_EMPTY, assessmentFeedbacks);
            }

            assessmentFeedbacks.setAssessmentFeedbackId(UUID.randomUUID().toString());

            assessmentFeedbacksService.save(assessmentFeedbacks);

            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    assessmentFeedbacks);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //增加用户评估
    @ApiOperation(value = "/addUserAssessment", notes = "添加用户评估测试")
    @PostMapping(value = "/addUserAssessment")
    public Result addUserAssessment(@RequestBody UserAssessmentDto userAssessmentDto) {
        try {

            if (userAssessmentDto == null || userAssessmentDto.getUserAssessmentFeedbacks() == null
                    || userAssessmentDto.getUserAssessmentFeedbacks().size() == 0) {
                return getResult(ResponseCode.GENERIC_FAILURE, userAssessmentDto);
            }

            Date currentTime = new Date();

            userAssessmentDto.getUserAssessmentFeedbacks().stream().forEach(item ->
            {
                item.setUserAssessmentfeedbackId(UUID.randomUUID().toString());

                item.setCreateTime(currentTime);

                userAssessmentDto.getFlagRemarks().stream().forEach(rebakItem -> {
                    if (rebakItem.assessmentId.equals(item.getAssessmentId())) {
                        item.setCoachRemark(rebakItem.getRemark());
                    }
                });

            });
            userAssessmentFeedbackService.saveBatch(userAssessmentDto.getUserAssessmentFeedbacks());

            List<UserAssessmentResource> resources = userAssessmentDto.getUserAssessmentResources();

            if(resources!=null && resources.size()>0)
            {
               resources.stream().forEach(item->{
                   item.setUserAssessmentresourceId(UUID.randomUUID().toString());

                   item.setCreateTime(currentTime);
               });
            }

            //Save recource
            userAssessmentResourceService.saveBatch(userAssessmentDto.getUserAssessmentResources());


            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    userAssessmentDto);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    //评估用户列表
    @ApiOperation(value = "/getTrainersAssessment", notes = "得到用户根据体检的列表,通过静态，动态，值")
    @PostMapping(value = "/getTrainersAssessment")
    public Result getTrainersAssessment(Integer assessmentType) {
        try {
            List<UserAssessmentListView> userAssessmentListViews =
                    userAssessmentListViewService.list(new QueryWrapper<UserAssessmentListView>()
                            .eq("assessment_type", assessmentType));

            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    userAssessmentListViews);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    //得到用户当时评估详细情况。
    @ApiOperation(value = "/getTrainerAssessmentByRecord", notes = "得到用户评估列表根据分类")
    @PostMapping(value = "/getTrainerAssessmentByRecord")
    public Result getTrainerAssessmentByRecord(@RequestBody UserAssessmentListView userAssessmentListView) {

        if (userAssessmentListView == null) {
            return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userAssessmentListView);
        }

        try {
            List<UserAssessmentListView> userAssessmentListViews =
                    userAssessmentListViewService.list(new QueryWrapper<UserAssessmentListView>()
                            .eq("assessment_type", userAssessmentListView.getAssessmentType())
                            .eq("user_id", userAssessmentListView.getUserId()));
            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    userAssessmentListViews);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    //得到用户评估细节。
    @ApiOperation(value = "/getTrainerAssessmentDetail", notes = "得到用户评估细节")
    @PostMapping(value = "/getTrainerAssessmentDetail")
    public Result getTrainerAssessmentDetail(@RequestBody UserAssessmentListView userAssessmentListView) {

        if (userAssessmentListView == null) {
            return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userAssessmentListView);
        }

        try {
            List<AssessmentContent> assessmentContents = assessmentContentService.list(
                    new QueryWrapper<AssessmentContent>()
                            .eq("assessment_type", userAssessmentListView.getAssessmentType())
                            .orderByAsc("show_order")
            );

            //用户保存的。
            List<UserAssessmentFeedback> userAssessmentFeedbacks = new ArrayList<>();
            userAssessmentFeedbacks = this.userAssessmentFeedbackService.list(new QueryWrapper<UserAssessmentFeedback>()
                    .eq("user_id",userAssessmentListView.getUserId())
                    .eq("coach_id",userAssessmentListView.getCoachId())
                    .eq("create_time",userAssessmentListView.getCreateTime()));

            List<String> userAssessmentFeedbackIds = userAssessmentFeedbacks.stream()
                    .map(UserAssessmentFeedback::getAssessmentFeedbackId).collect(Collectors.toList());

           Map<String,String> userSelectMap = new HashMap<String,String>();
            userAssessmentFeedbacks.stream().forEach(item->{
                userSelectMap.put(item.getAssessmentFeedbackId(),item.getAssessmentFeedbackValue());
            });

            //优化。 daniel
            if (assessmentContents != null && assessmentContents.size() > 0) {
                assessmentContents.stream().forEach(item -> {
                    //添加选择
                    List<AssessmentFeedbacks> assessmentFeedbacks = new ArrayList<>();

                    switch (userAssessmentListView.getAssessmentType())
                    {
                        case 0:
                            assessmentFeedbacks = assessmentFeedbacksService.list(new
                                    QueryWrapper<AssessmentFeedbacks>().eq(
                                    "owner", userAssessmentListView.getCoachId()).or().eq("owner", "system").eq(
                                    "assessment_id", item.assessmentId)
                                    .isNull("parent_id").orderByAsc("show_Order")
                            );
                            if (assessmentFeedbacks != null && assessmentFeedbacks.size() > 0) {
                                assessmentFeedbacks.stream().forEach(childItem -> {
                                    List<AssessmentFeedbacks> childFeedbacks = assessmentFeedbacksService.list(
                                            new QueryWrapper<AssessmentFeedbacks>().eq("parent_id", childItem.assessmentFeedbackId)
                                                    .in("assessment_feedback_id",userAssessmentFeedbackIds));
                                    childItem.setChildFeedbacks(childFeedbacks);
                                });
                            }
                            break;
                        case 1:
                            assessmentFeedbacks = assessmentFeedbacksService.list(new
                                    QueryWrapper<AssessmentFeedbacks>().eq(
                                    "owner", userAssessmentListView.getCoachId()).or().eq("owner", "system").eq(
                                    "assessment_id", item.assessmentId)
                                    .in("assessment_feedback_id",userAssessmentFeedbackIds).orderByAsc("show_Order")
                            );
                            break;
                        case 2:

                            assessmentFeedbacks = assessmentFeedbacksService.list(new
                                    QueryWrapper<AssessmentFeedbacks>().eq(
                                    "owner", userAssessmentListView.getCoachId()).or().eq("owner", "system").eq(
                                    "assessment_id", item.assessmentId)
                                    .in("assessment_feedback_id",userAssessmentFeedbackIds).orderByAsc("show_Order"));
                            if(assessmentFeedbacks!=null && assessmentFeedbacks.size()>0)
                            {
                                assessmentFeedbacks.stream().forEach(selectResult->{
                                  if(userSelectMap.containsKey(selectResult.getAssessmentFeedbackId()))                               {
                                      selectResult.setItemValue(userSelectMap.get(selectResult.getAssessmentFeedbackId()));
                                  }
                                });
                            }
                    }

                    item.setFeedbacks(assessmentFeedbacks);

                    List<UserAssessmentResource> resources = userAssessmentResourceService.list(new QueryWrapper<UserAssessmentResource>()
                            .eq("user_id",userAssessmentListView.getUserId())
                            .eq("coach_id",userAssessmentListView.getCoachId())
                            .eq("assessment_id",item.getAssessmentId())
                            .eq("create_time",userAssessmentListView.getCreateTime()));
                    if(resources!=null && resources.size()>0)
                    {

                        UserAssessmentResource userAssessmentResource = resources.get(0);
                        ResourceInfo resourceInfo = resourceInfoService.getById(userAssessmentResource.getResourceId());
                        if(resourceInfo!=null)
                        {
                            userAssessmentResource.setResourceUrl(resourceInfo.resourcePath);
                        }

                        item.setCurrentUserResource(resources.get(0));
                    }

                });
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    assessmentContents);

        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

}
