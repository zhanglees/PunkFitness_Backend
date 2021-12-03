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

}
