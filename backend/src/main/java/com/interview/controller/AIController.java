package com.interview.controller;

import org.springframework.web.bind.annotation.RestController;

import com.interview.dto.ai.AnswerEvaluationResponse;
import com.interview.service.AIService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
public class AIController {
    
    private final AIService aiService;

    @GetMapping("/test-ai")
    public List<String> testAI() {
        String resume = """
                Skills:
                Java
                Spring Boot
                Hibernate
                MySQL
                REST API
                Docker
                """;

        return aiService.generateInterviewQuestions(resume);
    }

    @GetMapping("/test-evaluate")
    public AnswerEvaluationResponse testEvaluate(){
        return aiService.evaluateAnswer("What is Spring Boot?", "Spring Boot is a Framework that simplifies Java application development.");
    }
    
    
}
