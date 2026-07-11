package com.interview.dto.interview;

import com.interview.enums.InterviewStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewSummaryResponse {
    
    private Long sessionId;

    private InterviewStatus status;

    private Integer totalQuestions;

    private Integer answeredQuestions;

    private Double averageScore;

    private String message;

}
