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
 *评估问卷
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("assessmentcontent")
public class AssessmentContent extends Model<AssessmentContent> {

    @TableId
    @TableField(value = "assessment_id")
    public String assessmentId;

    public String assessmentName;

    /**
     * 采集的类型 0 静态 1动态
     */
    public Integer assessmentType;

    @TableField(exist = false)
    List<AssessmentFeedbacks> feedbacks;


}
