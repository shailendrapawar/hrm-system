package com.hrm.hrm_system.modules.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserInputDTO implements IUserPayload {
    @Size(min=3,max=50,message="First name must be between 3 and 50")
    private String firstName;

    @Size(min=3,max=50,message="First name must be between 3 and 50")
    private String lastName;

    @Email(message = "invalid email")
    private String email;

//    @Size(min=5,max=50,message="Password must be between 3 and 50")
    private  String password;

    private Boolean isLocked;
    private Boolean isDeleted;
}
