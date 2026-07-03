package com.interview.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@JsonPropertyOrder({
    "sessionId",
    "title",
    "status",
    "totalQuestion",
    "answeredQuestions",
    "score"
})

@Data
@Builder
public class InterviewResultResponse {
    
    private Long sessionId;

    private String title;

    private String status ;

    private Integer totalQuestions;

    private Integer answeredQuestions;

    private Double score ;

}
