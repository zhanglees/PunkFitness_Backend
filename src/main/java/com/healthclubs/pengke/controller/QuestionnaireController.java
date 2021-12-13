package com.healthclubs.pengke.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.Question;
import com.healthclubs.pengke.entity.Questionitem;
import com.healthclubs.pengke.entity.UserAnswerChoice;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.service.IQuestionService;
import com.healthclubs.pengke.service.IQuestionitemService;
import com.healthclubs.pengke.service.IUserAnswerChoiceService;
import com.healthclubs.pengke.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Api("Questionnaire")
@RequestMapping("/api/questionnaire")
public class QuestionnaireController extends BaseController {

    @Autowired
    IUserService userService;

    @Autowired
    IQuestionService questionService;

    @Autowired
    IQuestionitemService questionitemService;

    @Autowired
    IUserAnswerChoiceService userAnswerChoiceService;


    @ApiOperation(value = "desc of method", notes = "1233")
    @RequestMapping(value = "/hello")
    public String say1() {
        Object t = userService.getById(27);
        String dd = userService.getUserAll().toString();
        return dd + "我是 cccccc";
    }


    //创建自定义标签
    @ApiOperation(value = "/creatCustomQuestionnaireLable", notes = "创建自定义标签")
    @PostMapping(value = "/creatCustomQuestionnaireLable")
    public Result CreatCustomQuestionnaireLable(@RequestBody Questionitem questionItem) {

        try {
            questionItem.setQuestionItemId(UUID.randomUUID().toString());
            questionitemService.save(questionItem);
            return getResult(ResponseCode.SUCCESS_PROCESSED,questionItem
                    );
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    @ApiOperation(value = "/getQuestionnaireAll", notes = "得到所有问卷")
    @RequestMapping(value = "/getQuestionnaireAll")
    public Result getQuestionnaireAll(String coachId) {
        try {
            List<Question> datas = new ArrayList<>();

            if (coachId == null || coachId.isEmpty()) {
                return getResult(ResponseCode.USER_COACHId_EMPTY);
            } else {
               // datas = this.questionService.list(new QueryWrapper<Question>().eq("owner_id", coachId).or().eq("owner_id", null));

                datas = this.questionService.list();

                if (datas != null && datas.size() > 0) {
                    datas.forEach(item->{
                      //  List<Questionitem> questionChilds = this.questionitemService.list(new QueryWrapper<Questionitem>()
                      //  .eq("owner_id",coachId).or().eq("owner_id",null).and(itan->itan.eq("qustion_id",item.questionId)));
                      List<Questionitem> questionChilds = this.questionitemService.list(
                              new QueryWrapper<Questionitem>().eq("qustion_id",item.getQuestionId()));

                      item.setItems(questionChilds);
                    });
                }
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED, datas);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/getQuestionByType", notes = "根据问卷类型，和教练id得到问卷内容")
    @RequestMapping(value = "/getQuestionByType")
    public Result getQuestionByType(String coachId,Integer type) {
        try {
            List<Question> datas = new ArrayList<>();

            if (coachId == null || coachId.isEmpty()) {
                return getResult(ResponseCode.USER_COACHId_EMPTY);
            } else {
                // datas = this.questionService.list(new QueryWrapper<Question>().eq("owner_id", coachId).or().eq("owner_id", null));

                String temptId ;
                if(type==null)
                {
                    return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY,type);
                }
                else
                {
                    //零时处理，要修改优化
                    if(type.equals(0))
                    {
                        temptId = "EDC0C14B-D8C9-4DB7-AFA7-0004B5BAC077";
                    }
                    else
                    {
                        temptId = "ce5078ff-ad07-103a-a787-e7e4c0162da0";
                    }
                }

                datas = this.questionService.list(new QueryWrapper<Question>().eq("question_template_id",temptId));

                if (datas != null && datas.size() > 0) {
                    datas.forEach(item->{
                        //  List<Questionitem> questionChilds = this.questionitemService.list(new QueryWrapper<Questionitem>()
                        //  .eq("owner_id",coachId).or().eq("owner_id",null).and(itan->itan.eq("qustion_id",item.questionId)));
                        List<Questionitem> questionChilds = this.questionitemService.list(
                                new QueryWrapper<Questionitem>().eq("qustion_id",item.getQuestionId()));

                        item.setItems(questionChilds);
                    });
                }
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED, datas);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    @ApiOperation(value = "/saveUserQuestion", notes = "保存用户答案")
    @PostMapping(value = "/saveUserQuestion")
    public Result saveUserQuestion(@RequestBody List<UserAnswerChoice> userAnswerChoices) {
        try {

            this.userAnswerChoiceService.saveBatch(userAnswerChoices);

            return getResult(ResponseCode.SUCCESS_PROCESSED, userAnswerChoices);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/getUserQuestionList", notes = "得到用户的问卷列表")
    @RequestMapping(value = "/getUserQuestionList")
    public Result getUserQuestionList(String coachId,String userId) {
        try {

          // this.userAnswerChoiceService.list(new QueryWrapper<UserAnswerChoice>()
          //         .eq("user_id",userId)
           //        .groupBy("coach_id","record_time"));
            return getResult(ResponseCode.SUCCESS_PROCESSED, this.userAnswerChoiceService.getUserQuestionList(userId));
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }




}
