package com.interview.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewHistoryResponse {
    
    private Long sessionId;

    private String title;

    private String status;

    private Integer totalQuestions;

    private Integer answeredQuestions;

    private Double score ;
}
