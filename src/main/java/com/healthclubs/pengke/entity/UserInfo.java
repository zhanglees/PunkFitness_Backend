package com.healthclubs.pengke.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;


//public class User extends Model<User> {
 //   private String userId;
 //   private String userName;

 //  public String getUserId() {
 //       return userId;
//   }

 //   public void setUserId(String userId) {
 //       this.userId = userId;
 //   }

 //   public String getUserName() {
 //       return userName;
 //   }

  //  public void setUserName(String userName) {
  ///      this.userName = userName;
   // }
//}


import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

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
@TableName("userInfo")
public class UserInfo  extends Model<UserInfo>{

    private static final long serialVersionUID = 1L;

    @TableId
    @TableField(value = "id")
    private String id;

    /**
     * 微信id
     */
    private  String wxid;


    /**
     * 姓名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private  String headImg;

    /**
     * 电话
     */
    private  String phone;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别
     */
    private  int sex;

    /**
     * 标记
     */
    private  String customerTag;

    /**
     * 备注
     */
    private  String remarks;

    /**
     * 学员级别
     */
    private  Integer coustomLevel;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}


