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
 *学员预约
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("userappointment")
public class UserAppointment extends Model<UserAppointment> {

    @TableId
    @TableField(value = "appointment_id")
    public String appointmentId;

    public String userId;

    public Date appointmentTime;

    public String classId;

    public Date createTime;

    /**
     * 预约是否完成。
     */
    public Integer isComplete;

    /**
     * 课程时长。例如 课程预计耗时60小时
     */
    @TableField(exist = false)
    private Integer classHour;


}
