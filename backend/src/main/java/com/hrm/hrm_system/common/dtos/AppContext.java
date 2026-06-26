package com.hrm.hrm_system.common.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AppContext {

    private String requestId;
    private LocalDateTime createdAt;
    private String ipAddress;

    private JWTUser user;
    private List<String> permissions;

}
