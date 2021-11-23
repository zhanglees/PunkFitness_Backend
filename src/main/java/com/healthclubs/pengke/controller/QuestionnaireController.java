package com.healthclubs.pengke.controller;


import com.healthclubs.pengke.pojo.Result;
import com.healthclubs.pengke.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("Questionnaire")
@RequestMapping("/api/questionnaire")
public class QuestionnaireController extends BaseController {


    @Autowired
    IUserService userService;

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
    public Result CreatCustomQuestionnaireLable() {
        return null;
    }

}
