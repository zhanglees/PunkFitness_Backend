package com.healthclubs.pengke.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *用户身体检查报告资源
 * </p>
 *
 * @author daniel.tz
 * @since new2021-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("userhealthcheckresource")
public class UserhealthcheckResource {

    @TableId
    @TableField(value = "user_healthcheckrecource_id")
    public String userHealthcheckrecourceId;

    public String userId;

    public String userHealthcheckId;

    public String itemLeftPath;

    public String itemRightPath;

    public String itemBackPath;

    public String itemAheadPath;

    public String healthReportPath;

}
