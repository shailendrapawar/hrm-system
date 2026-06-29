package com.hrm.hrm_system.modules.accessManagement.role.dtos;

import com.hrm.hrm_system.modules.accessManagement.role.enums.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreateRoleInputDTO implements IRolePayload {

    @NotBlank(message = "key is required")
    @Size(min = 2, max = 50, message = "Key must be between 2 and 50 characters")
    private String key;

    @NotBlank(message = "name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    private String description;

    private List<String> permissions = new ArrayList<>();
}
