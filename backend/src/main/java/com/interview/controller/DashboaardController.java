package com.interview.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.dto.response.DashboardResponse;
import com.interview.service.InterviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@Tag(
    name = "Dashboard Controller",
    description = "Used to see the user profile"
)
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashboaardController {
    
    private final InterviewService interviewService;

    @Operation(
        summary = "User Dashboard",
        description = "Returns interview statistics, average score, completed interviews and recent interview."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dashboard loaded successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> dashboard(){

        return ResponseEntity.ok(interviewService.getDashboard());
    }
    
}
