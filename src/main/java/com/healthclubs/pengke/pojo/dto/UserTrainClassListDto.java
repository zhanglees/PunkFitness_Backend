package com.healthclubs.pengke.pojo.dto;

import com.healthclubs.pengke.entity.UserTrainItem;

import java.util.List;

public class UserTrainClassListDto {

    public String getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(String trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public List<UserTrainItem> getUserTrainItems() {
        return userTrainItems;
    }

    public void setUserTrainItems(List<UserTrainItem> userTrainItems) {
        this.userTrainItems = userTrainItems;
    }

    public String trainingPlanId;

    public List<UserTrainItem> userTrainItems;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String userId;

    public String coachId;

}
