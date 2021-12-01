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
 *评估问卷 反馈
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("assessmentfeedbacks")
public class AssessmentFeedbacks extends Model<AssessmentFeedbacks> {


    @TableId
    @TableField(value = "assessment_feedback_id")
    public String assessmentFeedbackId;

    public String assessmentId;

    public String feedbackItem;

    public String itemValue;

    public String owner;

}
