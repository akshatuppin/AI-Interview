package com.interview.dto.interview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SubmitAnswerResponse {
    
    private Double score;

    private String feedback;

}
