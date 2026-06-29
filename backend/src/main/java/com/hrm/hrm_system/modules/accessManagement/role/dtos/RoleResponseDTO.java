package com.hrm.hrm_system.modules.accessManagement.role.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hrm.hrm_system.modules.accessManagement.role.enums.RoleStatus;
import com.hrm.hrm_system.modules.accessManagement.role.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResponseDTO {

    private String id;
    private String key;
    private String name;
    private String description;
    private List<String> permissions;
    private RoleType type;
    private RoleStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
