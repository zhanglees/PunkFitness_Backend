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

}
