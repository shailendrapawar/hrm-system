package com.hrm.hrm_system.modules.user;

import com.hrm.hrm_system.common.exception.AppException;
import com.hrm.hrm_system.common.response.ApiResponse;
import com.hrm.hrm_system.common.response.ResponseHandler;
import com.hrm.hrm_system.modules.user.dtos.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired UserService userService;
    @Autowired UserMapper userMapper;

    @GetMapping("/{keyword}") //GET SINGLE USER
    public ResponseEntity<ApiResponse<UserResponseDTO>> get(@PathVariable String keyword){
        Optional<UserEntity> user= this.userService.get(keyword);
        if(user.isEmpty()){
            throw new AppException("User not found", HttpStatus.NOT_FOUND);
        }
        UserResponseDTO data=userMapper.toDTO(user.get(),null);
        return ResponseHandler.send(HttpStatus.FOUND,"User found",data);
    }

    @GetMapping("")// SEARCH or GET USERS LIST
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> search(@ModelAttribute SearchUserFilters filters){

        List<UserEntity> users=userService.search(filters);

        List<UserResponseDTO>data=userMapper.toDTO(users,null);

        return ResponseHandler.send(HttpStatus.OK,"Users found ",data);
    }

    @PutMapping("/{id}") // UPDATE-USER
    public ResponseEntity<ApiResponse<UserResponseDTO>> update(@PathVariable String id, @RequestBody UpdateUserInputDTO payload){

        UserEntity user=userService.update(id,payload);

        UserResponseDTO data = userMapper.toDTO(user,null);

        return ResponseHandler.send(
                HttpStatus.OK,
                "User updated successfully",
                data
        );
    }




}
