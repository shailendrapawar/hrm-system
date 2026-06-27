package com.hrm.hrm_system.modules.user;

import com.hrm.hrm_system.common.dtos.AppContext;
import com.hrm.hrm_system.common.dtos.JWTUser;
import com.hrm.hrm_system.common.exception.AppException;
import com.hrm.hrm_system.common.response.ApiResponse;
import com.hrm.hrm_system.common.response.ResponseHandler;
import com.hrm.hrm_system.modules.user.dtos.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired UserService userService;
    @Autowired UserMapper userMapper;

    @GetMapping("/{keyword}") //GET SINGLE USER
    public ResponseEntity<ApiResponse<UserResponseDTO>> get(HttpServletRequest req, @PathVariable String keyword){
        //1: get app-context
        AppContext context=(AppContext) req.getAttribute("context");

        //2: call user service
        Optional<UserEntity> user = this.userService.get(keyword,context);
        if(user.isEmpty()){
            throw new AppException("User not found", HttpStatus.NOT_FOUND);
        }

        //3: map response
        UserResponseDTO data=userMapper.toDTO(user.get(),null);
        return ResponseHandler.send(HttpStatus.FOUND,"User found",data);
    }

    @GetMapping("/")// SEARCH or GET USERS LIST
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> search(HttpServletRequest req, @ModelAttribute SearchUserFilters filters){

        //1: get app-context
        AppContext context=(AppContext) req.getAttribute("context");
//
        //2: call service
        List<UserEntity> users=userService.search(filters,context);

        //3: map response
        List<UserResponseDTO>data=userMapper.toDTO(users,null);

        return ResponseHandler.send(HttpStatus.OK,"Users found ",data);
    }

    @PutMapping("/{id}") // UPDATE-USER
    public ResponseEntity<ApiResponse<UserResponseDTO>> update(HttpServletRequest req, @PathVariable String id, @RequestBody UpdateUserInputDTO payload){

        //1: get app-context
        AppContext context=(AppContext) req.getAttribute("context");

        //2: call service
        UserEntity user=userService.update(id,payload,context);

        //3: map response
        UserResponseDTO data = userMapper.toDTO(user,null);
        return ResponseHandler.send(
                HttpStatus.OK,
                "User updated successfully",
                data
        );
    }
}
