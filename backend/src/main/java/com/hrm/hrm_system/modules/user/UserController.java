package com.hrm.hrm_system.modules.user;

import com.hrm.hrm_system.modules.user.dtos.CreateIUserInputDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired UserService userService;

    @GetMapping("/{keyword}")
    public ResponseEntity get(@PathVariable String keyword){
        Optional<UserEntity> user= this.userService.get(keyword);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        return ResponseEntity.ok(user.get());
    }

    @PostMapping("/")
    public ResponseEntity create(@Valid @RequestBody CreateIUserInputDTO data){
        UserEntity user= userService.create(data);
        return ResponseEntity.ok(user);
    }
}
