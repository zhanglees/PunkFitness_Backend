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

    public String coachRemarks;

    public Integer stageFrequency;

    public Integer stagePeriod;


    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date createTime;

    @TableField(exist = false)
    public Integer completeSections;

    @TableField(exist = false)
    public String coachName;

    @TableField(exist = false)
    public String className;

}
