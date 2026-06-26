package com.hrm.hrm_system.modules.auth;

import com.hrm.hrm_system.common.dtos.AppContext;
import com.hrm.hrm_system.common.response.ApiResponse;
import com.hrm.hrm_system.common.response.ResponseHandler;
import com.hrm.hrm_system.common.utils.CookieHelper;
import com.hrm.hrm_system.modules.user.UserEntity;
import com.hrm.hrm_system.modules.user.dtos.CreateUserInputDTO;
import com.hrm.hrm_system.modules.auth.dtos.LoginUserInputDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    CookieHelper cookieHelper;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserEntity>> register(
            HttpServletRequest req,
            @Valid @RequestBody CreateUserInputDTO data)
    {
        //1: get app-context
        AppContext context=(AppContext) req.getAttribute("context");

        //2: call service
        Boolean isRegistered= authService.register(data,context);

        return ResponseHandler.send(HttpStatus.CREATED,"Users Created",null);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            HttpServletRequest req,
            @Valid @RequestBody LoginUserInputDTO payload,
            HttpServletResponse response){

        //1: get app-context
        AppContext context=(AppContext) req.getAttribute("context");

        //2: call service
        String token= authService.login(payload,context);

        // 3: set cookies
        Cookie cookie=cookieHelper.generateLoginCookies(token);
        response.addCookie(cookie);

        return ResponseHandler.send(HttpStatus.OK,"User logged in",token);
    }
}
