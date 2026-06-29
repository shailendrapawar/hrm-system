package com.hrm.hrm_system.modules.accessManagement.role;

import com.hrm.hrm_system.modules.accessManagement.role.enums.RoleType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    private String id;

    @Column(unique = true,nullable = false)
    private String key;

    private String description;

    @ElementCollection
    private List<String> permissions = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType type;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private  LocalDateTime updatedAt;

}
