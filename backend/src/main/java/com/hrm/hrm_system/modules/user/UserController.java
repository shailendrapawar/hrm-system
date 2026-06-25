package com.hrm.hrm_system.modules.user;

import com.hrm.hrm_system.common.exception.AppException;
import com.hrm.hrm_system.common.response.ApiResponse;
import com.hrm.hrm_system.common.response.ResponseHandler;
import com.hrm.hrm_system.modules.user.dtos.CreateUserInputDTO;
import com.hrm.hrm_system.modules.user.dtos.LoginUserInputDTO;
import com.hrm.hrm_system.modules.user.dtos.SearchUserFilters;
import com.hrm.hrm_system.modules.user.dtos.UpdateUserInputDTO;
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

    @GetMapping("/{keyword}") //GET SINGLE USER
    public ResponseEntity<ApiResponse<UserEntity>> get(@PathVariable String keyword){
        Optional<UserEntity> user= this.userService.get(keyword);
        if(user.isEmpty()){
            throw new AppException("User not found", HttpStatus.NOT_FOUND);
        }
        return ResponseHandler.send(HttpStatus.FOUND,"User found",user.get());
    }

    @GetMapping("")// SEARCH or GET USERS LIST
    public ResponseEntity<ApiResponse<List<UserEntity>>> search(@ModelAttribute SearchUserFilters filters){

        List<UserEntity> users=userService.search(filters);
        return ResponseHandler.send(HttpStatus.OK,"Users found ",users);
    }

    @PutMapping("/{id}") // UPDATE-USER
    public ResponseEntity<ApiResponse<UserEntity>> update(@PathVariable String id, @RequestBody UpdateUserInputDTO data){
        UserEntity user=userService.update(id,data);
        return ResponseHandler.send(
                HttpStatus.OK,
                "User updated successfully",
                user
        );
    }




}
