package com.healthclubs.pengke.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 学员保存训练课每节内容
 * </p>
 *
 * @author daniel.tz
 * @since new2021-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usertrainplansection")
public class UsertrainPlanSection extends Model<UsertrainPlanSection> {

    @TableId
    @TableField(value = "usertrain_section_id")
    public String usertrainSectionId;

    public String userId;

    public String trainingPlanId;

    public Date createTime;

    public String userTrainitemId;

    public String sectionName;

    public String coachId;

    public Date completeTime;

    @TableField(exist = false)
    public List<UserTraionSectionDetail> userTraionSectionDetails;
}
