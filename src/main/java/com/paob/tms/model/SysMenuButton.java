package com.paob.tms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_menu_button")
public class SysMenuButton {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long menuId;
    private String buttonName;
    private String buttonCode;
    private Integer buttonStatus;
    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;
} 