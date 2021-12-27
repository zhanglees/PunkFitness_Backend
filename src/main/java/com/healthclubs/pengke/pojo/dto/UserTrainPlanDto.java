package com.healthclubs.pengke.pojo.dto;

import com.healthclubs.pengke.entity.ClassInfo;
import com.healthclubs.pengke.entity.UserTrainingPlan;

import java.util.ArrayList;
import java.util.List;

public class UserTrainPlanDto {

    public List<ClassInfo> getClassInfos() {
        return classInfos;
    }

    public void setClassInfos(List<ClassInfo> classInfos) {
        this.classInfos = classInfos;
    }

    public UserTrainingPlan getUserTrainingPlan() {
        return userTrainingPlan;
    }

    public void setUserTrainingPlan(UserTrainingPlan userTrainingPlan) {
        this.userTrainingPlan = userTrainingPlan;
    }

    public List<ClassInfo> classInfos = new ArrayList<>();

    public UserTrainingPlan userTrainingPlan = new UserTrainingPlan();

}
