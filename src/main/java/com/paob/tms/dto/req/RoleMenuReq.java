package com.paob.tms.dto.req;

import lombok.Data;
import java.util.List;

@Data
public class RoleMenuReq {
    private Long menuId;
    private List<Long> buttonIds;
} 