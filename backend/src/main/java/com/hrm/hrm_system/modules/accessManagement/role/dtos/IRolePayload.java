package com.hrm.hrm_system.modules.accessManagement.role.dtos;

import java.util.List;

public interface IRolePayload {
    String getKey();
    String getName();
    String getDescription();
    List<String> getPermissions();
}
