package com.hrm.hrm_system.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter

@AllArgsConstructor
public class JWTUser {
    private String id;
    private String email;
    private List<String> roles;
}
