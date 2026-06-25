package com.hrm.hrm_system.modules.auth;

import com.hrm.hrm_system.common.response.ApiResponse;
import com.hrm.hrm_system.common.response.ResponseHandler;
import com.hrm.hrm_system.modules.user.UserEntity;
import com.hrm.hrm_system.modules.user.UserService;
import com.hrm.hrm_system.modules.user.dtos.CreateUserInputDTO;
import com.hrm.hrm_system.modules.user.dtos.LoginUserInputDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserEntity>> register(@Valid @RequestBody CreateUserInputDTO data){
        Boolean isRegistered= authService.register(data);
        return ResponseHandler.send(HttpStatus.CREATED,"Users Created",null);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginUserInputDTO payload, HttpServletResponse response){
        String token= authService.login(payload);

        //set cookies
        Cookie cookie=new Cookie("token",token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);

        return ResponseHandler.send(HttpStatus.OK,"User logged in",token);
    }
}
