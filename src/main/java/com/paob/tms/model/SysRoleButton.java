package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_role_button")
public class SysRoleButton {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long roleId;
    private Long buttonId;
    private String createdBy;
    private LocalDateTime createdTime;
} 