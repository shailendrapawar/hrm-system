package com.hrm.hrm_system.modules.accessManagement.role.dtos;

import com.hrm.hrm_system.modules.accessManagement.role.enums.RoleStatus;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateRoleInputDTO implements IRolePayload {

    @Size(min = 2, max = 50, message = "Key must be between 2 and 50 characters")
    private String key;

    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    private String description;

    private List<String> permissions;

    private RoleStatus status;
}
