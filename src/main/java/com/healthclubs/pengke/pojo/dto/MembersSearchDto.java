package com.healthclubs.pengke.pojo.dto;

public class MembersSearchDto {

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public Integer getTrainerType() {
        return trainerType;
    }

    public void setTrainerType(Integer trainerType) {
        this.trainerType = trainerType;
    }

    String condition;

    String coachId;

    Integer trainerType ;

}
