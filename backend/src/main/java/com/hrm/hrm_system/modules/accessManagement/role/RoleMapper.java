package com.hrm.hrm_system.modules.accessManagement.role;

import com.hrm.hrm_system.common.dtos.JWTUser;
import com.hrm.hrm_system.modules.accessManagement.role.dtos.RoleResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleMapper {

    public RoleResponseDTO toDTO(RoleEntity role, JWTUser currentUser) {
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setId(role.getId());
        dto.setKey(role.getKey());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        dto.setPermissions(role.getPermissions());
        dto.setType(role.getType());
        dto.setStatus(role.getStatus());
        dto.setCreatedAt(role.getCreatedAt());
        dto.setUpdatedAt(role.getUpdatedAt());
        return dto;
    }

    public List<RoleResponseDTO> toDTO(List<RoleEntity> roles, JWTUser currentUser) {
        List<RoleResponseDTO> result = new ArrayList<>();
        for (RoleEntity role : roles) {
            result.add(this.toDTO(role, currentUser));
        }
        return result;
    }
}
