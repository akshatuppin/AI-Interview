package com.interview.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.interview.enums.InterviewStatus;

import lombok.Builder;
import lombok.Data;

@JsonPropertyOrder({
    "sessionId",
    "title",
    "status",
    "totalQuestions",
    "answeredQuestions",
    "score",
    "questions"
})

@Data
@Builder
public class InterviewDetailResponse {
    
    private Long sessionId;

    private String title;

    private InterviewStatus status;

    private Integer totalQuestions;

    private Integer answeredQuestions;

    private Double score;

    private List<InterviewQuestionDetailResponse> questions;

}
