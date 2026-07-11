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
import com.interview.service.InterviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Tag(
    name = "Interview Management",
    description = "APIs for conducting AI mock Interviews"
)
@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService
            interviewService;

    // private final InterviewHistoryResponse interviewHistoryResponse;

    @Operation(
        summary = "Used to start the interview",
        description = "Generates interview questions using AI based on the analyzed resume."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Interview started successfully"),
        @ApiResponse(responseCode = "404", description = "Resume not found"),
        @ApiResponse(responseCode = "400", description = "Resume not analyzed"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/start/{id}")
    public StartInterviewResponse
    startInterview(
            @PathVariable("id") Long resumeId) {

        return interviewService
                .startInterviewResponse(resumeId);
    }

    @Operation(
        summary = "Get Interview Questions",
        description = "Returns all interview questons for a particular interview session."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Questions fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Interview sesion not found")
    })
    @GetMapping("/{sessionId}/questions")
    public List<QuestionResponse> getQuestion(@PathVariable Long sessionId){
        
        return interviewService.getQuestions(sessionId);

    }

    @Operation(
        summary = "Submit Answer",
        description = "Submits an answer for a question, evaluates it using AI, and stores the score and feedback."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Answer submitted seccessfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "404", description = "Questions not found")
    })
    @PostMapping("/questions/{questionId}/answer")
    public String submitAnswer(@PathVariable Long questionId, @Valid @RequestBody SubmitAnswerRequest request){
        interviewService.submitAnswer(questionId, request);

        return "Answer Submitted successfully";
    }

    // @PostMapping("/{sessionId}/complete")
    // public String completeInterview(@PathVariable Long sessionId) {
    //     //TODO: process POST request
        
    //     interviewService.completeInterview(sessionId);

    //     return "Interview Completed Successfully";
    // }

    @Operation(
        summary = "Interview Result",
        description = "Returns the completed interview results including all questions, answers, feedback and the final score"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Result fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Interview session not found")
    })
    @GetMapping("/{sessionId}/result")
    public ResponseEntity<com.interview.dto.response.InterviewResultResponse> getInterviewResultResponse(@PathVariable Long sessionId) {
        return ResponseEntity.ok(
            interviewService.getInterviewResult(sessionId)
        );
    }

    
    
    @Operation(
        summary = "Interview History",
        description = "Returns all inetrview completed or started by the logged-in user."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "History featched successfully"),
        @ApiResponse(responseCode = "401", description= "Unauthorized")
    })
    @GetMapping("/history")
    public List<InterviewHistoryResponse> getInterviewHistory(){
        return interviewService.getInterviewHistory();
    }


    @Operation(
        summary = "Interview Deatils",
        description = "Returns detailed information of a specific interview session."
    )    
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Interview details fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Interview session not found")
    })
    @GetMapping("/{sessionId}")
    public InterviewDetailResponse getInterviewDetails(@PathVariable Long sessionId){
        return interviewService.getInterviewDetails(sessionId);
    }

    @Operation(
        summary = "Get the next Question",
        description = "Returns the next unanswered interview question."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Questions fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Interview session not found")
    })
    @GetMapping("/{sessionId}/next-question")
    public ResponseEntity<NextQuestionResponse> getNextQuestion(@PathVariable Long sessionId){
        return ResponseEntity.ok(
            interviewService.getNextQuestionResponse(sessionId)
        );
    }
    
    @Operation(
        summary = "Complete Interview",
        description = "Completes the interview, calculates the final score, and returns the interview summary."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Interview completed successfully"),
        @ApiResponse(responseCode = "404", description = "Interview session not found")
    })
    @PostMapping("/{sessionId}/complete")
    public ResponseEntity<InterviewSummaryResponse> completeInterview(@PathVariable Long sessionId){
        return ResponseEntity.ok(
            interviewService.compeleteInterview(sessionId)
        );
    }

    
    
    
}