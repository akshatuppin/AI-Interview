package com.interview.service;

import java.util.List;

import com.interview.dto.ai.AnswerEvaluationResponse;

public interface AIService {
    
    List<String> generateInterviewQuestions(String resumeText);

    AnswerEvaluationResponse evaluateAnswer(String question, String answer);
}
