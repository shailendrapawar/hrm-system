package com.hrm.hrm_system.modules.auth;

import com.hrm.hrm_system.common.exception.AppException;
import com.hrm.hrm_system.common.utils.JWTHelper;
import com.hrm.hrm_system.modules.user.UserEntity;
import com.hrm.hrm_system.modules.user.UserService;
import com.hrm.hrm_system.modules.user.dtos.CreateUserInputDTO;
import com.hrm.hrm_system.modules.auth.dtos.LoginUserInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    UserService userService;
    @Autowired
    JWTHelper jwtHelper;

    public String login (LoginUserInputDTO payload){

        Optional<UserEntity> entity=userService.get(payload.getEmail());

        if(entity.isEmpty()){
            throw  new AppException("Account not found with these credentials", HttpStatus.NOT_FOUND);
        }

        UserEntity user=entity.get();

        //1: match password
        if(!user.getPassword().equals(payload.getPassword())){
            throw  new AppException("Invalid credentials",HttpStatus.BAD_REQUEST);
        }

        //2: create token
        String token = jwtHelper.generateToken(user);
        return token;
    }

    public Boolean register(CreateUserInputDTO payload){
        UserEntity user= userService.create(payload);
        if(user==null){
            throw new AppException("Issue while registering the user",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return true;
    }
}
