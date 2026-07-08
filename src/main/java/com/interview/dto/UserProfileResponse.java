package com.interview.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import lombok.Builder;
import lombok.Data;

@JsonPropertyOrder({
    "id",
    "firstName",
    "lastName",
    "email",
    "phoneNumber",
    "role",
    "collegeName",
    "branch",
    "graduationYear",
    "githubUrl",
    "linkedinUrl",
    "protfolioUrl"
})


@Data
@Builder
public class UserProfileResponse {
    

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String role ;

    private String collegeName;

    private String branch;

    private String graduationYear;

    private String githubUrl;

    private String linkedinUrl;

    private String protfolioUrl;
}
