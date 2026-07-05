package com.interview.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.interview.dto.InterviewDetailResponse;
import com.interview.dto.InterviewHistoryResponse;
import com.interview.dto.QuestionResponse;
import com.interview.dto.StartInterviewResponse;
import com.interview.dto.SubmitAnswerRequest;
import com.interview.dto.interview.InterviewSummaryResponse;
import com.interview.dto.interview.NextQuestionResponse;
import com.interview.dto.response.DashboardResponse;
import com.interview.service.InterviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService
            interviewService;

    // private final InterviewHistoryResponse interviewHistoryResponse;

    @PostMapping("/start/{id}")
    public StartInterviewResponse
    startInterview(
            @PathVariable("id") Long resumeId) {

        return interviewService
                .startInterviewResponse(resumeId);
    }

    @GetMapping("/{sessionId}/questions")
    public List<QuestionResponse> getQuestion(@PathVariable Long sessionId){
        
        return interviewService.getQuestions(sessionId);

    }

    @PostMapping("/questions/{questionId}/answer")
    public String submitAnswer(@PathVariable Long questionId, @RequestBody SubmitAnswerRequest request){
        interviewService.submitAnswer(questionId, request);

        return "Answer Submitted successfully";
    }

    // @PostMapping("/{sessionId}/complete")
    // public String completeInterview(@PathVariable Long sessionId) {
    //     //TODO: process POST request
        
    //     interviewService.completeInterview(sessionId);

    //     return "Interview Completed Successfully";
    // }

    @GetMapping("/{sessionId}/result")
    public ResponseEntity<com.interview.dto.response.InterviewResultResponse> getInterviewResultResponse(@PathVariable Long sessionId) {
        return ResponseEntity.ok(
            interviewService.getInterviewResult(sessionId)
        );
    }

    
    
    
    @GetMapping("/history")
    public List<InterviewHistoryResponse> getInterviewHistory(){
        return interviewService.getInterviewHistory();
    }

    @GetMapping("/{sessionId}")
    public InterviewDetailResponse getInterviewDetails(@PathVariable Long sessionId){
        return interviewService.getInterviewDetails(sessionId);
    }

    @GetMapping("/{sessionId}/next-question")
    public ResponseEntity<NextQuestionResponse> getNextQuestion(@PathVariable Long sessionId){
        return ResponseEntity.ok(
            interviewService.getNextQuestionResponse(sessionId)
        );
    }
    
    @PostMapping("/{sessionId}/complete")
    public ResponseEntity<InterviewSummaryResponse> completeInterview(@PathVariable Long sessionId){
        return ResponseEntity.ok(
            interviewService.compeleteInterview(sessionId)
        );
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> dashboard(){

        return ResponseEntity.ok(interviewService.getDashboard());
    }
    
    
}