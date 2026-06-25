package com.hrm.hrm_system.modules.user.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hrm.hrm_system.common.dtos.JWTUser;
import com.hrm.hrm_system.modules.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private List<String> roles;

}
