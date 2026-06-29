package com.hrm.hrm_system.modules.accessManagement.role.dtos;

import com.hrm.hrm_system.modules.accessManagement.role.enums.RoleStatus;
import com.hrm.hrm_system.modules.accessManagement.role.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRoleFilters {
    private String keyword;
    private RoleStatus status;
    private RoleType type;
}
