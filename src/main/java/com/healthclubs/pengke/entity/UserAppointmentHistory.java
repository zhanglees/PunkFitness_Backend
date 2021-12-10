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
 *学员预约
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("userappointmenthistory")
public class UserAppointmentHistory extends Model<UserAppointmentHistory> {

    @TableId
    @TableField(value = "singin_id")
    public String singinId;

    public String appointmentId;

    public String classId;

    public Date singinTime;

    public Integer trainingType;

    @TableField(exist = false)
    public String userId;

    @TableField(exist = false)
    public String userName;

}
