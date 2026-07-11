package com.interview.service;

import com.interview.dto.ProfileRequest;
import com.interview.dto.UserProfileResponse;

public interface UserService {
    
    // UserProfileResponse getCurrentUser();

    UserProfileResponse getProfile();

    UserProfileResponse updateProfile(ProfileRequest request);

}
