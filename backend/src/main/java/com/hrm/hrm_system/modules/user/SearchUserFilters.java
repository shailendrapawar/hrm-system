package com.hrm.hrm_system.modules.user;

import lombok.Data;
import lombok.Getter;

@Getter
public class SearchUserFilters {
    private String nameKeyword;
    private Boolean isLocked;
    private Boolean isDeleted;
}
