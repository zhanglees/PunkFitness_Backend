package com.healthclubs.pengke.pojo.dto;

import com.healthclubs.pengke.entity.UsertrainPlanSection;

public class CopyPlanSectionClassDto {

    public UsertrainPlanSection getUsertrainPlanSection() {
        return usertrainPlanSection;
    }

    public void setUsertrainPlanSection(UsertrainPlanSection usertrainPlanSection) {
        this.usertrainPlanSection = usertrainPlanSection;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    public UsertrainPlanSection usertrainPlanSection = new UsertrainPlanSection();

    public Integer showOrder;
}
