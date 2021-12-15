package com.healthclubs.pengke.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


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
@TableName("question")
public class Question extends Model<Question> {

    @TableId
    @TableField(value = "question_id")
    public String questionId;

    /**
     * 问卷模板id
     */
    public String questionTemplateId;

    public Integer required;

    public String remark;

    public String questionAnswer;

    public String questionContent;

    /**
     * 类型 1单选2多选，3问答，4判断。
     */
    public Integer questionType;

    public String ownerId;

    //题集
    @TableField(exist = false)
    private List<Questionitem> items;

    public String showOrder;

    @TableField(exist = false)
    private String itemExplain;

}
