package com.interview.service.impl;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.interview.dto.AuthResponse;
import com.interview.dto.LoginRequest;
import com.interview.dto.RegisterRequest;
import com.interview.entity.Role;
import com.interview.entity.User;
import com.interview.repository.RoleRepository;
import com.interview.repository.UserRepository;
import com.interview.security.JwtService;
import com.interview.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public String register(RegisterRequest request) {
        // TODO Auto-generated method stub
        
        if(userRepository.existsByEmail(
                request.getEmail())) {

            throw new RuntimeException(
                    "Email already exists");
        }

        Role studentRole =
                roleRepository.findByRoleName(
                        "ROLE_STUDENT")
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Role not found"));
                                        

        User user = new User();

        user.setFirstName(
                request.getFirstName());

        user.setLastName(
                request.getLastName());

        user.setEmail(
                request.getEmail());

        user.setPhoneNumber(
                request.getPhoneNumber());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        System.out.println(studentRole.getId());
        System.out.println(studentRole.getRoleName());

        user.setRole(studentRole);
        userRepository.save(user);      

        return "User Registered Successfully";
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // TODO Auto-generated method stub
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> 
                        new RuntimeException("User not Found"));

        boolean isValid = passwordEncoder.matches(
                request.getPassword(), user.getPassword());

        if( !isValid){
                throw new RuntimeException("Invalid Credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        return  new AuthResponse(token, user.getEmail(), user.getRole().getRoleName());
    }
    
}
