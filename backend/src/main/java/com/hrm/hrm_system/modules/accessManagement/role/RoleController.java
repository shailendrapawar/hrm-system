package com.hrm.hrm_system.modules.accessManagement.role;

import com.hrm.hrm_system.common.dtos.AppContext;
import com.hrm.hrm_system.common.dtos.JWTUser;
import com.hrm.hrm_system.common.exception.AppException;
import com.hrm.hrm_system.common.response.ApiResponse;
import com.hrm.hrm_system.common.response.ResponseHandler;
import com.hrm.hrm_system.modules.accessManagement.role.dtos.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired RoleService roleService;
    @Autowired RoleMapper roleMapper;

    @GetMapping("/{keyword}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> get(HttpServletRequest req, @PathVariable String keyword) {
        AppContext context = (AppContext) req.getAttribute("context");

        Optional<RoleEntity> role = roleService.get(keyword, context);
        if (role.isEmpty()) {
            throw new AppException("Role not found", HttpStatus.NOT_FOUND);
        }

        RoleResponseDTO data = roleMapper.toDTO(role.get(), context.getUser());
        return ResponseHandler.send(HttpStatus.OK, "Role found", data);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> search(HttpServletRequest req, @ModelAttribute SearchRoleFilters filters) {
        AppContext context = (AppContext) req.getAttribute("context");

        List<RoleEntity> roles = roleService.search(filters, context);

        List<RoleResponseDTO> data = roleMapper.toDTO(roles, context.getUser());
        return ResponseHandler.send(HttpStatus.OK, "Roles found", data);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> create(HttpServletRequest req, @Valid @RequestBody CreateRoleInputDTO payload) {
        AppContext context = (AppContext) req.getAttribute("context");

        RoleEntity role = roleService.create(payload, context);

        RoleResponseDTO data = roleMapper.toDTO(role, context.getUser());
        return ResponseHandler.send(HttpStatus.CREATED, "Role created successfully", data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> update(HttpServletRequest req, @PathVariable String id, @Valid @RequestBody UpdateRoleInputDTO payload) {
        AppContext context = (AppContext) req.getAttribute("context");

        RoleEntity role = roleService.update(id, payload, context);

        RoleResponseDTO data = roleMapper.toDTO(role, context.getUser());
        return ResponseHandler.send(HttpStatus.OK, "Role updated successfully", data);
    }
}
