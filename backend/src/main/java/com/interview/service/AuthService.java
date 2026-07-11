package com.interview.service;

import com.interview.dto.AuthResponse;
import com.interview.dto.LoginRequest;
import com.interview.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
    
} 
