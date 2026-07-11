package com.interview.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@JsonPropertyOrder({
    "message",
    "sessionId",
    "title",
    "totalQuestions"
})

@Data
@Builder
public class StartInterviewResponse {
    
    private Long sessionId;

    private String message;

    private Integer totalQuestions;

    private String title;
}
