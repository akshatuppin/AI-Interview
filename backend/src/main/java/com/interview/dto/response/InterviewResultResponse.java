package com.interview.dto.response;

import java.util.List;

import com.interview.enums.InterviewStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewResultResponse {
    
    private Long sessionId;

    private String title;

    private InterviewStatus status;

    private Integer totalQuestions;

    private Integer answeredQuestions;

    private Double averageScore;

    private String overallFeedback;

    private List<QuestionResultResponse> questions;

}
