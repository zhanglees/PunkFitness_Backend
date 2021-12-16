package com.healthclubs.pengke.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.healthclubs.pengke.entity.Question;
import com.healthclubs.pengke.entity.Questionitem;
import com.healthclubs.pengke.entity.UserAnswerChoice;
import com.healthclubs.pengke.exception.PengkeException;
import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.pojo.dto.UserQuestionDetailDto;
import com.healthclubs.pengke.pojo.dto.UserQuestionViewDto;
import com.healthclubs.pengke.service.IQuestionService;
import com.healthclubs.pengke.service.IQuestionitemService;
import com.healthclubs.pengke.service.IUserAnswerChoiceService;
import com.healthclubs.pengke.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
            return getResult(ResponseCode.SUCCESS_PROCESSED, questionItem
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
                    datas.forEach(item -> {
                        //  List<Questionitem> questionChilds = this.questionitemService.list(new QueryWrapper<Questionitem>()
                        //  .eq("owner_id",coachId).or().eq("owner_id",null).and(itan->itan.eq("qustion_id",item.questionId)));
                        List<Questionitem> questionChilds = this.questionitemService.list(
                                new QueryWrapper<Questionitem>().eq("qustion_id", item.getQuestionId()));

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
    public Result getQuestionByType(String coachId, Integer type) {
        try {
            List<Question> datas = new ArrayList<>();

            if (coachId == null || coachId.isEmpty()) {
                return getResult(ResponseCode.USER_COACHId_EMPTY);
            } else {
                // datas = this.questionService.list(new QueryWrapper<Question>().eq("owner_id", coachId).or().eq("owner_id", null));

                String temptId;
                if (type == null) {
                    return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, type);
                } else {
                    //零时处理，要修改优化
                    if (type.equals(0)) {
                        temptId = "EDC0C14B-D8C9-4DB7-AFA7-0004B5BAC077";
                    } else {
                        temptId = "ce5078ff-ad07-103a-a787-e7e4c0162da0";
                    }
                }

                datas = this.questionService.list(new QueryWrapper<Question>().eq("question_template_id", temptId));

                if (datas != null && datas.size() > 0) {
                    datas.forEach(item -> {
                        //  List<Questionitem> questionChilds = this.questionitemService.list(new QueryWrapper<Questionitem>()
                        //  .eq("owner_id",coachId).or().eq("owner_id",null).and(itan->itan.eq("qustion_id",item.questionId)));
                        List<Questionitem> questionChilds = this.questionitemService.list(
                                new QueryWrapper<Questionitem>().eq("qustion_id", item.getQuestionId()));

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

            if (userAnswerChoices == null) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userAnswerChoices);
            } else {
                //当前日期
                Date currentDate = new Date();

                userAnswerChoices.stream().forEach(item -> {
                    item.setRecordTime(currentDate);
                });
            }

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
    public Result getUserQuestionList(String coachId, String userId) {
        try {
            // this.userAnswerChoiceService.list(new QueryWrapper<UserAnswerChoice>()
            //         .eq("user_id",userId)
            //        .groupBy("coach_id","record_time"));
            List<UserQuestionDetailDto> userQuestionDetailDtos = new ArrayList<>();
            List<UserAnswerChoice> userAnswerChoices = this.userAnswerChoiceService.getUserQuestionList(userId);

            if (userAnswerChoices != null && userAnswerChoices.size() > 0) {
                userAnswerChoices.stream().forEach(item -> {
                    UserQuestionDetailDto userQuestionDetailDto = new UserQuestionDetailDto();
                    userQuestionDetailDto.setRecordTime(item.getRecordTime());
                    userQuestionDetailDto.setCoachId(item.getCoachId());
                    userQuestionDetailDto.setQuestionType(item.getQuestionType());
                    userQuestionDetailDto.setUserId(item.getUserId());
                    userQuestionDetailDto.setCoachName(item.getCoachName());
                    userQuestionDetailDtos.add(userQuestionDetailDto);
                });
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED, userQuestionDetailDtos);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/getUserQuestionListByType", notes = "根据用户分类得到问卷列表")
    @RequestMapping(value = "/getUserQuestionListByType")
    public Result getUserQuestionListByType(String coachId, String userId, Integer type) {
        try {
            // this.userAnswerChoiceService.list(new QueryWrapper<UserAnswerChoice>()
            //         .eq("user_id",userId)
            //        .groupBy("coach_id","record_time"));
            List<UserQuestionDetailDto> userQuestionDetailDtos = new ArrayList<>();
            List<UserAnswerChoice> userAnswerChoices = this.userAnswerChoiceService.getUserQuestionListByType(userId);

            if (type != null) {
                userAnswerChoices = userAnswerChoices.stream().filter(item -> item.getQuestionType() ==
                        type).collect(Collectors.toList());
            }

            if (userAnswerChoices != null && userAnswerChoices.size() > 0) {
                userAnswerChoices.stream().forEach(item -> {
                    UserQuestionDetailDto userQuestionDetailDto = new UserQuestionDetailDto();
                    userQuestionDetailDto.setRecordTime(item.getRecordTime());
                    userQuestionDetailDto.setCoachId(item.getCoachId());
                    userQuestionDetailDto.setQuestionType(item.getQuestionType());
                    userQuestionDetailDto.setUserId(item.getUserId());
                    userQuestionDetailDto.setCoachName(item.getCoachName());
                    userQuestionDetailDtos.add(userQuestionDetailDto);
                });
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED, userQuestionDetailDtos);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/getQuestionDetail", notes = "得到用户的问卷详细")
    @PostMapping(value = "/getQuestionDetail")
    public Result getQuestionDetail(UserQuestionDetailDto userQuestionDetailDto) {
        try {

            List<UserAnswerChoice> allChoices =
                    this.userAnswerChoiceService.list(new QueryWrapper<UserAnswerChoice>().eq("user_id", userQuestionDetailDto.userId)
                            .eq("coach_id", userQuestionDetailDto.coachId)
                            .eq("record_time", userQuestionDetailDto.recordTime)
                            .eq("question_type", userQuestionDetailDto.questionType)
                    );
            return getResult(ResponseCode.SUCCESS_PROCESSED, allChoices);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }

    @ApiOperation(value = "/saveUserTemplateQuestion", notes = "根据模板保存用户选中")
    @PostMapping(value = "/saveUserTemplateQuestion")
    public Result saveUserTemplateQuestion(@RequestBody UserQuestionViewDto userQuestionViewDto) {
        try {

            if (userQuestionViewDto == null || userQuestionViewDto.getQuestions() == null) {
                return getResult(ResponseCode.PARAMETER_CANNOT_EMPTY, userQuestionViewDto);
            }

            List<UserAnswerChoice> userAnswerChoices = new ArrayList<>();

            String userId = userQuestionViewDto.getUserId();
            String coachId = userQuestionViewDto.getCoachId();
            Date currentDate = new Date();
            Integer templateType0 = 0;//模板类型。0健身 1健康
            Integer templatyType1 = 1;

            userQuestionViewDto.getQuestions().stream().forEach(item -> {
                item.getItems().stream().forEach(childItem -> {
                    UserAnswerChoice tempData = new UserAnswerChoice();
                    tempData.setUserId(userId);
                    tempData.setCoachId(coachId);
                    tempData.setRecordTime(currentDate);
                    //优化。
                    if (item.getQuestionTemplateId().equals("EDC0C14B-D8C9-4DB7-AFA7-0004B5BAC077")) {
                        tempData.setQuestionType(templateType0);
                    } else {
                        tempData.setQuestionType(templatyType1);
                    }
                    tempData.setQuestionItemId(childItem.getQuestionItemId());
                    tempData.setItemExplain(item.getItemExplain());
                    userAnswerChoices.add(tempData);
                });
            });

            if (userAnswerChoices.size() > 0) {
                this.userAnswerChoiceService.saveBatch(userAnswerChoices);
            }

            return getResult(ResponseCode.SUCCESS_PROCESSED);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


    @ApiOperation(value = "/getUserTemplateQuestionDetail", notes = "得到用户的问卷详细")
    @PostMapping(value = "/getUserTemplateQuestionDetail")
    public Result getUserTemplateQuestionDetail(UserQuestionDetailDto userQuestionDetailDto) {
        try {

            List<String> questionItemids =
                    this.userAnswerChoiceService.list(new QueryWrapper<UserAnswerChoice>()
                            .eq("user_id", userQuestionDetailDto.userId)
                            .eq("coach_id", userQuestionDetailDto.coachId)
                            .eq("record_time", userQuestionDetailDto.recordTime)
                            .eq("question_type", userQuestionDetailDto.questionType)
                    ).stream().map(UserAnswerChoice::getQuestionItemId)
                            .collect(Collectors.toList());

            List<Questionitem> questionitemsitems = this.questionitemService.list(new QueryWrapper<Questionitem>().
                    in("question_item_id", questionItemids));


            List<UserAnswerChoice> questionsData = this.userAnswerChoiceService.
                    getUserQuestionExplain(userQuestionDetailDto.userId).stream().filter(item -> {
                if (item.getRecordTime().equals(userQuestionDetailDto.recordTime) &&
                        item.getQuestionType().equals(userQuestionDetailDto.questionType)) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());

            List<Question> questions = new ArrayList<>();
            if(userQuestionDetailDto.questionType.equals(0))
            {
                //有问题 优化！！！
                questions = this.questionService.list(new QueryWrapper<Question>()
                        .eq("question_template_id","EDC0C14B-D8C9-4DB7-AFA7-0004B5BAC077"));
            }
            else
            {
                //有问题 优化！！！
                questions = this.questionService.list(new QueryWrapper<Question>()
                        .eq("question_template_id","ce5078ff-ad07-103a-a787-e7e4c0162da0"));
            }



            questions.stream().forEach(item -> {
                item.setItems(questionitemsitems.stream().filter(childitem -> {
                    if (childitem.getQustionId().equals(item.getQuestionId())) {
                        return true;
                    } else {
                        return false;
                    }
                }).collect(Collectors.toList()));

                questionsData.stream().forEach(questionItem -> {
                    if (questionItem.getQustionId().equals(item.getQuestionId())) {
                        item.setItemExplain(questionItem.getItemExplain());
                    }
                });

            });

            UserQuestionViewDto userQuestionViewDto = new UserQuestionViewDto();
            userQuestionViewDto.setUserId(userQuestionDetailDto.userId);
            userQuestionViewDto.setCreateTime(userQuestionDetailDto.recordTime);
            userQuestionViewDto.setCoachId(userQuestionDetailDto.coachId);
            userQuestionViewDto.setQuestions(questions);


            return getResult(ResponseCode.SUCCESS_PROCESSED, userQuestionViewDto);
        } catch (PengkeException e) {
            return getResult(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return getResult(ResponseCode.GENERIC_FAILURE);
        }
    }


}
