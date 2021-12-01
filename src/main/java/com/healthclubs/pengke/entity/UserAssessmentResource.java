package com.healthclubs.pengke.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <p>
 * 保存用户评估问卷 资源
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("userassessmentresource")
public class UserAssessmentResource extends Model<UserAssessmentResource> {


    @TableId
    @TableField(value = "user_assessmentresource_id")
    public String userAssessmentresourceId;

    public String userId;

    public String coachId;

    public String assessmentId;

    public String resourceId;

    public Date createTime;


}
