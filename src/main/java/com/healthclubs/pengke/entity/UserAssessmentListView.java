package com.healthclubs.pengke.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("userAssessmentList")
public class UserAssessmentListView {

    @TableField(value = "assessment_feedback_id")
    public String assessmentFeedbackId;

    public Integer assessmentType;

    public String userId;

    public String userName;

    public Date createTime;



}
