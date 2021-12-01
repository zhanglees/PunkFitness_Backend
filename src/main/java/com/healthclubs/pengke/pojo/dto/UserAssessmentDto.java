package com.healthclubs.pengke.pojo.dto;

import com.healthclubs.pengke.entity.AssessmentContent;
import com.healthclubs.pengke.entity.UserAssessmentFeedback;
import com.healthclubs.pengke.entity.UserAssessmentResource;

import java.util.ArrayList;
import java.util.List;

public class UserAssessmentDto {

    List<UserAssessmentFeedback> userAssessmentFeedbacks = new ArrayList<>();

    public List<UserAssessmentFeedback> getUserAssessmentFeedbacks() {
        return userAssessmentFeedbacks;
    }

    public void setUserAssessmentFeedbacks(List<UserAssessmentFeedback> userAssessmentFeedbacks) {
        this.userAssessmentFeedbacks = userAssessmentFeedbacks;
    }

    public List<UserAssessmentResource> getUserAssessmentResources() {
        return userAssessmentResources;
    }

    public void setUserAssessmentResources(List<UserAssessmentResource> userAssessmentResources) {
        this.userAssessmentResources = userAssessmentResources;
    }

    List<UserAssessmentResource> userAssessmentResources = new ArrayList<>();
}
