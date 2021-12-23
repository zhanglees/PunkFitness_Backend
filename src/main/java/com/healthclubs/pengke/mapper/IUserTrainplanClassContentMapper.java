package com.healthclubs.pengke.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthclubs.pengke.entity.UserTrainplanClassContent;

import java.util.List;

public interface IUserTrainplanClassContentMapper extends BaseMapper<UserTrainplanClassContent> {

    List<UserTrainplanClassContent> getTrainClassItemContent(String classId,String trainingPlanId,String userId,String coachId);
}
