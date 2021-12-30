package com.healthclubs.pengke.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.healthclubs.pengke.entity.TempClassNums;
import com.healthclubs.pengke.entity.UserTrainItem;

import java.util.List;

public interface IUserTrainItemMapper extends BaseMapper<UserTrainItem> {

    List<UserTrainItem> getUserClassByCoachId(String userId,String coachId);


    TempClassNums getUserCompleteClass(String userId);

}
