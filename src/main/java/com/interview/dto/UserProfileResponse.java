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
    "role"
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
}
