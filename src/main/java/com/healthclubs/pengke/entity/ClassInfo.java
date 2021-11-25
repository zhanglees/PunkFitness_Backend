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
 *课程信息
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("classinfo")
public class ClassInfo extends Model<ClassInfo> {

    @TableId
    @TableField(value = "class_id")
    public String classId;

    public String className;

    /**
     * 课程时长。例如 课程预计耗时60小时
     */
    public Integer classHour;

    /**
     * 训练类型（0体能，1力量，2力量2，3体能2）
     */
    public Integer trainingType;

}
