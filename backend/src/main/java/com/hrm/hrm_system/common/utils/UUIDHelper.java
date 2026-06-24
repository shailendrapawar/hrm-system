package com.hrm.hrm_system.common.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDHelper {
    public String generate(){
        return UUID.randomUUID().toString();
    }
    public String generate(String prefix){
        return prefix+UUID.randomUUID().toString();
    }

    public boolean isValid(String value) {

        if (value == null || value.isBlank()) {
            return false;
        }

        try {
            UUID.fromString(value);
            return true;

        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
