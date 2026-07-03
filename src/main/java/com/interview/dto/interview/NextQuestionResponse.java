package com.interview.dto.interview;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NextQuestionResponse {
    
    private Long questionId;

    private Integer questionOrder;

    private String questionText;

}
