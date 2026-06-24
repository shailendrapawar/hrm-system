package com.hrm.hrm_system.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseHandler {
    public static <T> ResponseEntity<ApiResponse<T>> send(
            HttpStatus status,
            String message,
            T data
    ) {

        ApiResponse<T> response = new ApiResponse<>(
                status.value(),
                message,
                data
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
