package com.interview.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileRequest {
    
    @Size(max = 100, message = "College name cannot exceed 100 characters")
    private String collegeName;

    @Size(max = 50, message = "Branch cannot exceed 50 characters")
    private String branch ;

    @Pattern(
        regexp =  "^(19|20)\\d{2}$",
        message = "Graduation year must be vlid 4-digit years"
    )
    private String graduationYear;

    @Pattern(
        regexp = "^(https?://)?(www\\.)?github\\.com/.*$",
        message = "Enter a valid GitHub URL"
    )
    private String githubUrl;

    @Pattern(
            regexp = "^(https?://)?(www\\.)?linkedin\\.com/.*$",
            message = "Enter a valid LinkedIn URL"
    )
    private String linkedinUrl;

     @Pattern(
            regexp = "^(https?://).*$",
            message = "Enter a valid Portfolio URL"
    )
    private String protfolioUrl ;

}
