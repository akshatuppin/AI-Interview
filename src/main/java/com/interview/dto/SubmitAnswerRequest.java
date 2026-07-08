package com.interview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubmitAnswerRequest {
    
    @NotBlank(message = "Answer cannot be empty")
    private String answer ;
}
