package com.hrm.hrm_system.modules.user;

import com.hrm.hrm_system.common.dtos.JWTUser;
import com.hrm.hrm_system.modules.user.dtos.UserResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserMapper {
    public UserResponseDTO toDTO(UserEntity user, JWTUser currentUser){

        UserResponseDTO dto=new UserResponseDTO();
        //1: ADD common fields here
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        // ADMIN view fields
        if(currentUser!=null && currentUser.getRoles().contains("admin") ){
            dto.setStatus(user.getStatus());
            dto.setEmail(user.getEmail());
            dto.setCreatedAt(user.getCreatedAt());
            dto.setUpdatedAt(user.getUpdatedAt());
        }

        return dto;
    }

    public List<UserResponseDTO> toDTO(List<UserEntity> items, JWTUser currentUser){
        List<UserResponseDTO> mappedUsers=new ArrayList<>();
        for (UserEntity i : items) {
            UserResponseDTO item = this.toDTO(i, currentUser);
            mappedUsers.add(item);
        }
        return  mappedUsers;
    }
}
