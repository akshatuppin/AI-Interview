package com.interview.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.dto.ProfileRequest;
import com.interview.dto.UserProfileResponse;
import com.interview.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(
    name = "User Profile",
    description = "APIs for managing user profile"
)
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;


    @Operation(
        summary = "Get User Profile",
        description = "Returns the profile information of the current logged-in user."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profile fetched successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(){
        return ResponseEntity.ok(userService.getProfile());
    }

    @Operation(
        summary = "Used to update the User Profile",
        description = "Updates the profile details of the logged-in user."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "400", description = "Invalid profile data")
    })
    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(@Valid @RequestBody ProfileRequest request){
        return ResponseEntity.ok(userService.updateProfile(request));
    }
    
    

}
