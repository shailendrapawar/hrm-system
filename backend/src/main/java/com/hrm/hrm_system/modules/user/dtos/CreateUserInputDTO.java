package com.hrm.hrm_system.modules.user.dtos;

import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserInputDTO implements IUserPayload {
    @NotBlank(message = "first name is required")
    @Size(min=3,max=50,message="First name must be between 3 and 50")
    private String firstName;

    @Size(min=0,max=50,message="First name must be between 0 and 50")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
}
