package com.hrm.hrm_system.modules.accessManagement.userRole;

import com.hrm.hrm_system.modules.accessManagement.role.RoleEntity;
import com.hrm.hrm_system.modules.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_roles")
public class UserRoleEntity {
    @Id
    private String id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(
            name="user_id",
            nullable=false
    )
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "role_id",
            nullable = false
    )
    private RoleEntity role;

    @CurrentTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String assignedBy;
}
