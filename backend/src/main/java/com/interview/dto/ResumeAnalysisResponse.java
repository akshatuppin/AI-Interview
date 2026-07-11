package com.interview.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({
    "id",
    "skills",
    "projects",
    "education",
    "experience"
})
public class ResumeAnalysisResponse {
    
    private Long id;

    private String skills ;

    private String projects;

    private String education;

    private String experience;

}
