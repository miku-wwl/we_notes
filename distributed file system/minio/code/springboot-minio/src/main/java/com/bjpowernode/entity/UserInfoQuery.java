package com.bjpowernode.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_user_info
 */
@Data
public class UserInfoQuery implements Serializable {

    /**
     * 主键id自增
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别（0女 1男）
     */
    private Integer sex;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 住址
     */
    private String address;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String userImage;

    private String userContract;

}