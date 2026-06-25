package com.hrm.hrm_system.common.exception;

import com.hrm.hrm_system.common.response.ApiResponse;
import com.hrm.hrm_system.common.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Object>> handleAppException(
            AppException ex
    ){
//        System.out.println("========== ERROR ==========");
//        ex.printStackTrace();
//        System.out.println("===========================");
        return ResponseHandler.send(
                ex.getStatus(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneral(
            Exception ex
    ){
        return ResponseHandler.send(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong",
                null
        );
    }
}