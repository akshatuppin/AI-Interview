package com.interview.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    
    @NotBlank(message = "Email is required")
    @Email(message = "please enter a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
