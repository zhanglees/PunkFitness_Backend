package com.healthclubs.pengke.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学员保存训练课内容
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usertrainplanclasscontent")
public class UserTrainplanClassContent extends Model<UserTrainplanClassContent> {

    @TableId
    @TableField(value = "user_classcontent_id")
    public String userClasscontentId;

    public String classId;

    public String classContentId;

    public String trainingPlanId;

    public String userId;

    public String coachId;

    public String contentItemValue;

}
