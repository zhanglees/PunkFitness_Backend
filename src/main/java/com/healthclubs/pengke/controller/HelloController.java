package com.healthclubs.pengke.controller;


import com.healthclubs.pengke.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    IUserService userService;

    @ApiOperation(value = "desc of method", notes = "1233")
    @RequestMapping(value = "/hello")
    public String say() {
        Object t= userService.getById(27);
        String dd = userService.getUserAll().toString();
        return dd+"我是 cccccc" ;
    }
}