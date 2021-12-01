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
 *资源
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("resourceinfo")
public class ResourceInfo extends Model<ResourceInfo> {


    @TableId
    @TableField(value = "resource_id")
    public String resourceId;

    public Integer resourceType;

    public  String creater;

    public String owner;

    public String resourcePath;

    public String createName;

    public String ownerName;

    public Date createTime;
}
