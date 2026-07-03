package com.interview.dto.interview;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewSummaryResponse {
    
    private Long sessionId;

    private String status;

    private Integer totalQuestions;

    private Integer answeredQuestions;

    private Double averageScore;

    private String message;

}
