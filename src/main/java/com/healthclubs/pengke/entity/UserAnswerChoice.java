package com.healthclubs.pengke.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("useranswerchoice")
public class UserAnswerChoice extends Model<UserAnswerChoice> {

    public String userId;

    public String questionItemId;

    public String coachId;

    public Date recordTime;

    public Integer questionType;

    //教练名称
    @TableField(exist = false)
    public String coachName;

}
