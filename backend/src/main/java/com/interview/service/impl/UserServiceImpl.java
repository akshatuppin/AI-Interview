package com.interview.service.impl;

import com.interview.dto.ProfileRequest;
import com.interview.dto.UserProfileResponse;
import com.interview.entity.User;
import com.interview.entity.UserProfile;
import com.interview.repository.UserProfileRepository;
import com.interview.repository.UserRepository;
import com.interview.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

        
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        // User user =
        //         userRepository.findByEmail(email)
        //                 .orElseThrow(() ->
        //                         new RuntimeException(
        //                                 "User not found"));

//         return User.builder()
//                 .id(user.getId())
//                 .firstName(user.getFirstName())
//                 .lastName(user.getLastName())
//                 .email(user.getEmail())
//                 .phoneNumber(user.getPhoneNumber())
//                 .role(user.getRole().getRoleName())
//                 .build();

        return userRepository.findByEmail(email)
                        .orElseThrow(() -> 
                                new RuntimeException("User not found")
                        );

     }    

    @Override
    public UserProfileResponse getProfile() {
        User user = getCurrentUser();

        UserProfile profile = userProfileRepository
                .findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found")); 
        
        return UserProfileResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().getRoleName())
                .collegeName(profile.getCollegeName())
                .branch(profile.getBranch())
                .graduationYear(profile.getGraduationYear())
                .githubUrl(profile.getGithubUrl())
                .linkedinUrl(profile.getLinkedinUrl())
                .protfolioUrl(profile.getPortfolioUrl())

                .build();
    }


    @Override
    public UserProfileResponse updateProfile(ProfileRequest request) {
        User user = getCurrentUser();

        UserProfile profile = userProfileRepository
                .findByUser(user)
                .orElse(new UserProfile());
        

        profile.setUser(user);
        profile.setCollegeName(request.getCollegeName());
        profile.setBranch(request.getBranch());
        profile.setGraduationYear(request.getGraduationYear());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setPortfolioUrl(request.getProtfolioUrl());

        userProfileRepository.save(profile);

        return UserProfileResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().getRoleName())
                .collegeName(profile.getCollegeName())
                .branch(profile.getBranch())
                .graduationYear(profile.getGraduationYear())
                .githubUrl(profile.getGithubUrl())
                .linkedinUrl(profile.getLinkedinUrl())
                .protfolioUrl(profile.getPortfolioUrl())
                .build();
    }
}