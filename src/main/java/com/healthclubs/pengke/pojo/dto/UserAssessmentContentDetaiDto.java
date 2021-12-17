package com.healthclubs.pengke.pojo.dto;

import com.healthclubs.pengke.entity.AssessmentContent;
import com.healthclubs.pengke.entity.UserAssessmentResource;

public class UserAssessmentContentDetaiDto extends AssessmentContent {

    public UserAssessmentResource getUserAssessmentResource() {
        return userAssessmentResource;
    }

    public void setUserAssessmentResource(UserAssessmentResource userAssessmentResource) {
        this.userAssessmentResource = userAssessmentResource;
    }

    public UserAssessmentResource userAssessmentResource;
}
