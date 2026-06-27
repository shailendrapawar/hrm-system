package com.hrm.hrm_system.modules.user.dtos;

import com.hrm.hrm_system.modules.user.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserFilters {
    private String nameKeyword;
    private UserStatus status;
}
