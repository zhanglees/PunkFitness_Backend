package com.healthclubs.pengke.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 训练计划
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usertrainingplan")
public class UserTrainingPlan extends Model<UserTrainingPlan> {

    @TableId
    @TableField(value = "training_plan_id")
    public String trainingPlanId;

    public String userId;

    public Integer frequencies;

    public Integer totalPeriod;

    public String coachId;

    public Integer isShow;

    @TableField(exist = false)
    public List<UserTrainItem> userTrainItems;
}
