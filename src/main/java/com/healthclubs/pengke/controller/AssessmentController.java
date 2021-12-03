package com.healthclubs.pengke.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.AssessmentContent;
import com.healthclubs.pengke.entity.AssessmentFeedbacks;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.UserAssessmentDto;
import com.healthclubs.pengke.service.IAssessmentContentService;
import com.healthclubs.pengke.service.IAssessmentFeedbacksService;
import com.healthclubs.pengke.service.IUserAssessmentFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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
                    List<AssessmentFeedbacks> assessmentFeedbacks = assessmentFeedbacksService.list(new QueryWrapper<AssessmentFeedbacks>().eq(
                            "owner", coachId).or().eq("owner", "system").eq("assessment_id", item.assessmentId)
                    );
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

            userAssessmentDto.getUserAssessmentFeedbacks().stream().forEach(item->
            {
                item.setUserAssessmentfeedbackId(UUID.randomUUID().toString());
            });
            userAssessmentFeedbackService.saveBatch(userAssessmentDto.getUserAssessmentFeedbacks());

            return getResult(ResponseCode.SUCCESS_PROCESSED,
                    userAssessmentDto);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

}
