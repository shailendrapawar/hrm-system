package com.hrm.hrm_system.modules.user.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserFilters {
    private String nameKeyword;
    private Boolean isLocked;
    private Boolean isDeleted;
}
