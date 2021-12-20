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
 *用户身体检查报告
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("userhealthcheckreport")
public class UserhealthcheckReport extends Model<UserhealthcheckReport> {

    @TableId
    @TableField(value = "user_healthcheck_id")
    public String userHealthcheckId;

    public String userId;

    public String userName;

    public String coachId;

    public Double weight;

    public Double bmi;

    public Float bodyFatRatio;

    public Integer height;

    public Integer age;

    public Float waistline;

    public Float chestMeasurement;

    public Date createTime;

    /**
     * 脂肪量
     */
    public Float fatContent;

    /**
     * 水分
     */
    public Float waterContent;

    /**
     * 骨骼肌
     */
    public Float skeletalMuscle;

    /**
     * 肌肉量
     */
    public Float muscleMass;

    /**
     * 腰臀比
     */
    public Float waistHipRatio;

    /**
     * 臀围
     */
    public Float hipline;

    /**
     * 左上臂围
     */
    public Float leftArmCircumference;

    /**
     * 右上臂围
     */
    public Float rightArmCircumference;

    /**
     * 左大腿围
     */
    public Float leftThighCircumference;

    /**
     * 右大腿围
     */
    public Float rightThighCircumference;

    /**
     * 左小腿围
     */
    public Float leftShankCircumference;

    /**
     * 右小腿围
     */
    public Float rightShankCircumference;

    @TableField(exist = false)
    private String  coachName;
}
