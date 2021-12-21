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
 * 训练课程条目
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usertrainitem")
public class UserTrainItem extends Model<UserTrainItem> {

    @TableId
    @TableField(value = "user_trainitem_id")
    public String userTrainitemId;

    public String trainingPlanId;

    public String classId;

    public String userId;

    public Integer classNum;

    public String coachId;

    @TableField(exist = false)
    List<UserTrainplanClassContent> userTrainplanClassContents;


}
