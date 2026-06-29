package com.hrm.hrm_system.modules.accessManagement.role;

import com.hrm.hrm_system.common.dtos.AppContext;
import com.hrm.hrm_system.common.exception.AppException;
import com.hrm.hrm_system.common.utils.UUIDHelper;
import com.hrm.hrm_system.modules.accessManagement.role.dtos.*;
import com.hrm.hrm_system.modules.accessManagement.role.enums.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    private UUIDHelper uuidHelper;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // EXPORT METHODS ================================================= >
    // SET
    public RoleEntity set(RoleEntity entity, IRolePayload model, AppContext context) {
        if (StringUtils.hasText(model.getKey())) {
            entity.setKey(model.getKey());
        }
        if (StringUtils.hasText(model.getName())) {
            entity.setName(model.getName());
        }
        if (StringUtils.hasText(model.getDescription())) {
            entity.setDescription(model.getDescription());
        }
        if (model.getPermissions() != null) {
            entity.setPermissions(model.getPermissions());
        }

        if (model instanceof UpdateRoleInputDTO updateModel) {
            if (updateModel.getStatus() != null) {
                entity.setStatus(updateModel.getStatus());
            }
        }

        return entity;
    }

    // GET
    public Optional<RoleEntity> get(String keyword, AppContext context) {
        if (!StringUtils.hasText(keyword)) {
            throw new AppException("Invalid identifier", HttpStatus.BAD_REQUEST);
        }

        if (uuidHelper.isValid(keyword)) {
            return roleRepository.findById(keyword);
        }
        return roleRepository.findByKey(keyword);
    }

    // SEARCH
    public List<RoleEntity> search(SearchRoleFilters filters, AppContext context) {
        Specification<RoleEntity> spec = Specification.where((r, q, cb) -> cb.conjunction());

        if (StringUtils.hasText(filters.getKeyword())) {
            String kw = "%" + filters.getKeyword().toLowerCase() + "%";
            spec = spec.and((r, q, cb) ->
                    cb.or(
                            cb.like(cb.lower(r.get("key")), kw),
                            cb.like(cb.lower(r.get("description")), kw)
                    ));
        }

        if (filters.getStatus() != null) {
            spec = spec.and((r, q, cb) -> cb.equal(r.get("status"), filters.getStatus()));
        }

        if (filters.getType() != null) {
            spec = spec.and((r, q, cb) -> cb.equal(r.get("type"), filters.getType()));
        }

        return roleRepository.findAll(spec);
    }

    // CREATE
    public RoleEntity create(CreateRoleInputDTO model, AppContext context) {
        Optional<RoleEntity> existing = this.get(model.getKey(), context);
        if (existing.isPresent()) {
            throw new AppException("Role already exists with this key", HttpStatus.BAD_REQUEST);
        }

        RoleEntity newRole = new RoleEntity();
        newRole.setId(uuidHelper.generate());
        newRole.setType(RoleType.CUSTOM);

        newRole = this.set(newRole, model, context);
        return roleRepository.save(newRole);
    }

    // UPDATE
    public RoleEntity update(String id, UpdateRoleInputDTO model, AppContext context) {
        Optional<RoleEntity> entity = this.get(id, context);

        if (entity.isEmpty()) {
            throw new AppException("Role not found", HttpStatus.NOT_FOUND);
        }

        RoleEntity role = this.set(entity.get(), model, context);
        return roleRepository.save(role);
    }
}
