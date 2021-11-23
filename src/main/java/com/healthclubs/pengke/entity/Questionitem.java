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
 *
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("questionitem")
public class Questionitem  extends Model<Questionitem> {

    @TableId
    @TableField(value = "question_item_id")
    public String questionItemId;

    public String qustionId;

    public String describes;

    public String isAnswer;

    public String ownerId;

}
