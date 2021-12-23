package com.healthclubs.pengke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthclubs.pengke.entity.UserTrainplanClassContent;

import java.util.List;

public interface IUserTrainplanClassContentService extends IService<UserTrainplanClassContent> {

    List<UserTrainplanClassContent> getTrainClassItemContent(String classId,String trainingPlanId,String userId,String coachId);
}
