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
 * 学员训练小节保存细节
 * </p>
 *
 * @author daniel.tz
 * @since new2021-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usertraionsectiondetail")
public class UserTraionSectionDetail extends Model<UserTraionSectionDetail> {

    @TableId
    @TableField(value = "section_detail_id")
    public String sectionDetailId;

    public String usertrainSectionId;

    public String sectionName;

    public Integer trainingArea;

    public Integer trainingType;

    public Integer action;

    public Integer equipment;

    public Integer counterWeight;

    public Integer groups;

    public Integer numberSinglegroup;

    public String videourl;

    public String userId;

    public String coachId;
}
