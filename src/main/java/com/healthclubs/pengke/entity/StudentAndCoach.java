package com.healthclubs.pengke.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("StudentAndCoach")
public class StudentAndCoach  extends Model<StudentAndCoach> {
    private static final long serialVersionUID = 1L;

    @TableId
    @TableField(value = "student_id")
    private String studentId;

    @TableId
    @TableField(value = "coach_id")
    private String coachId;


    private Date recordTime;


    @Override
    protected Serializable pkVal() {
        return null;
    }


}
