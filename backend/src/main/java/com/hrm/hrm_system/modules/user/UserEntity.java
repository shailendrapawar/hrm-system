package com.hrm.hrm_system.modules.user;

import com.hrm.hrm_system.modules.accessManagement.userRole.UserRoleEntity;
import com.hrm.hrm_system.modules.user.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    // 1
    @Id
    private String id;

    // 2
    @NotBlank(message = "first name is required")
    private String firstName;
    private String lastName;

    // 3
    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    @Column(unique = true, nullable = false)
    private String email;

    // 4
    @NotBlank(message = "password is required")
    private String password;

    // 5
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status= UserStatus.INACTIVE;

    // 6
    @CreationTimestamp
    private LocalDateTime createdAt;

    // 7
    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    @OneToMany(
//            mappedBy = "user",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<UserRoleEntity> roles = new ArrayList<>();
}
