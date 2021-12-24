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
 *训练课程内容
 * </p>
 *
 * @author daniel.tz
 * @since 2021-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("classcontent")
public class ClassContent extends Model<ClassContent> {

    @TableId
    @TableField(value = "class_content_id")
    public String classContentId;

    public String trainClassId;

    public Integer trainTarg;

    public String itemName;

    public String itemValue;

    public String owner;

    @TableField(exist = false)
    public boolean userChose =false;

}
