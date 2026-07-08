package com.interview.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.dto.AuthResponse;
import com.interview.dto.LoginRequest;
import com.interview.dto.RegisterRequest;
import com.interview.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Tag(
    name = "Authentication",
    description = "APIs for user registeration and login"
)

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @Operation(
        summary = "Register User",
        description = "Creates a new user account."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request or user already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register (@Valid @RequestBody RegisterRequest request){
        //TODO: process POST request
        
        String response = authService.register(request);

        return ResponseEntity.ok(response);
    }


    @Operation(
        summary = "Login User",
        description = "Authenticates the user and returns a JWT token."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login Successfull"),
        @ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
       
        return ResponseEntity.ok(authService.login(request));
    }
    
    

}
