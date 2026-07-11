package com.interview.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewQuestionDetailResponse {
    
    private Long questionId;

    private String questionText;

    private String userAnswer;

    private Integer questionOrder;

    private Double score;

    private String feedback;
}
