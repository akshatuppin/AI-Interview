package com.interview.dto.interview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmiteAnswerRequest {
    
    private Long questionId;

    private String answer;
}
