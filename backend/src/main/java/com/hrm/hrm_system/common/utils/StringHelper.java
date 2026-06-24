package com.hrm.hrm_system.common.utils;

import org.springframework.stereotype.Component;

@Component
public class StringHelper {
    public Boolean isValidEmail(String keyword){
        if (keyword == null || keyword.isBlank()) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!keyword.matches(emailRegex)) {
            return false;
        }

        return true;
    }
}
