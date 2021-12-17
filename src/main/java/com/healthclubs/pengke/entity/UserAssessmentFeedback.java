package com.healthclubs.pengke.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * <p>
 * 保存用户评估问卷 反馈
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("userassessmentfeedback")
public class UserAssessmentFeedback extends Model<UserAssessmentFeedback> {


    @TableId
    @TableField(value = "user_assessmentfeedback_id")
    public String userAssessmentfeedbackId;

    public String userId;

    public String coachId;

    public String assessmentFeedbackId;

    public String assessmentFeedbackValue;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date createTime;

    public String coachRemark;

    @TableField(exist = false)
    public String assessmentId;
}
