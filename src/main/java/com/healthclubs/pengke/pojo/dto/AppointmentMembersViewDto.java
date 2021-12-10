package com.healthclubs.pengke.pojo.dto;

import com.healthclubs.pengke.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class AppointmentMembersViewDto {

    public List<UserInfo> getTrainners() {
        return trainners;
    }

    public void setTrainners(List<UserInfo> trainners) {
        this.trainners = trainners;
    }

    public Integer getTrainnerNums() {
        return trainnerNums;
    }

    public void setTrainnerNums(Integer trainnerNums) {
        this.trainnerNums = trainnerNums;
    }

    public List<UserInfo> trainners = new ArrayList<>();

    public Integer trainnerNums = 0;
}
