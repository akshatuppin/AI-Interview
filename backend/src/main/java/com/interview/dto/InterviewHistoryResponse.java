package com.interview.dto;

import com.interview.enums.InterviewStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewHistoryResponse {
    
    private Long sessionId;

    private String title;

    private InterviewStatus status;

    private Integer totalQuestions;

    private Integer answeredQuestions;

    private Double score ;
}
