package com.healthclubs.pengke.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.healthclubs.pengke.entity.TempClassNums;
import com.healthclubs.pengke.entity.UserTrainItem;

import java.util.List;

public interface IUserTrainItemService extends IService<UserTrainItem> {

    List<UserTrainItem> getUserClassByCoachId(String userId, String coachId);

    TempClassNums getUserCompleteClass(String userId);

}
