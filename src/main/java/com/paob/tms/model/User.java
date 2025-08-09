package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("sys_user")
public class User  {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(exist = false)
    private String password;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String iv;
    private String umNo;
    private String userName;
    private String email;
    private String departmentName;
    private Integer userStatus;
}