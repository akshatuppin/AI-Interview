package com.interview.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionResultResponse {
    
    private Integer questionOrder;

    private String questionText;

    private String userAnswer;

    private Double score;

    private String feedback;

}
