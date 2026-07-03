package com.interview.service;

import java.util.List;

import com.interview.dto.InterviewDetailResponse;
import com.interview.dto.InterviewHistoryResponse;
import com.interview.dto.InterviewResultResponse;
import com.interview.dto.QuestionResponse;
import com.interview.dto.StartInterviewResponse;
import com.interview.dto.SubmitAnswerRequest;
import com.interview.dto.interview.InterviewSummaryResponse;
import com.interview.dto.interview.NextQuestionResponse;
import com.interview.dto.interview.SubmitAnswerResponse;

public interface InterviewService {

    StartInterviewResponse startInterviewResponse(Long resumeId);


    List<QuestionResponse> getQuestions(Long sessionId);

    SubmitAnswerResponse submitAnswer(Long questionId, SubmitAnswerRequest request);

    // void completeInterview(Long sessionId);

    InterviewResultResponse getInterviewResult(Long sessionId);

    List<InterviewHistoryResponse> getInterviewHistory();

    InterviewDetailResponse getInterviewDetails(Long sessionId);

    NextQuestionResponse getNextQuestionResponse(Long sesionId);

    InterviewSummaryResponse compeleteInterview(Long sessionId); 

}
