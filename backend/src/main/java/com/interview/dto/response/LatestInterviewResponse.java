package com.interview.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LatestInterviewResponse {
    
    private Long sessionId;

    private String title;

    private String status;

    private Double score;


}
