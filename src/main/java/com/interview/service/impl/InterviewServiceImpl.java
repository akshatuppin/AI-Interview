package com.interview.service.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.interview.dto.InterviewDetailResponse;
import com.interview.dto.InterviewHistoryResponse;
import com.interview.dto.InterviewQuestionDetailResponse;
import com.interview.dto.InterviewResultResponse;
import com.interview.dto.QuestionResponse;
import com.interview.dto.StartInterviewResponse;
import com.interview.dto.SubmitAnswerRequest;
import com.interview.dto.ai.AnswerEvaluationResponse;
import com.interview.dto.interview.InterviewSummaryResponse;
import com.interview.dto.interview.NextQuestionResponse;
import com.interview.dto.interview.SubmitAnswerResponse;
import com.interview.entity.InterviewQuestion;
import com.interview.entity.InterviewSession;
import com.interview.entity.Resume;
import com.interview.entity.ResumeAnalysis;
import com.interview.entity.User;
import com.interview.repository.InterviewQuestionRepository;
import com.interview.repository.InterviewSessionRepository;
import com.interview.repository.ResumeAnalysisRepository;
import com.interview.repository.ResumeRepository;
import com.interview.repository.UserRepository;
import com.interview.service.AIService;
import com.interview.service.InterviewService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService{

    private final ResumeRepository resumeRepository;

    private final ResumeAnalysisRepository resumeAnalysisRepository;

    private final InterviewSessionRepository interviewSessionRepository;

    private final InterviewQuestionRepository interviewQuestionRepository;

    private final UserRepository userRepository;

    private final AIService aiService;


    @Override
    public StartInterviewResponse startInterviewResponse(Long resumeId) {
        // TODO Auto-generated method stub
        

        // Gives the information of Current Logged-in User

        Authentication authentication = 
                SecurityContextHolder
                    .getContext()
                    .getAuthentication();

            
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> 
                        new RuntimeException(
                            "User not found"
                        ));

        Resume resume = resumeRepository.findById(resumeId)
                        .orElseThrow( () ->
                            new RuntimeException(
                                "Resume not found"
                            ));

        ResumeAnalysis analysis = resumeAnalysisRepository
                                .findByResume(resume)
                                .orElseThrow(() ->
                                    new RuntimeException(
                                        "Analysis resume first"
                                    ));

        String resumeText = """
                Summary:
                %s

                Skills:
                %s

                Experience:
                %s

                Projects:
                %s

                Education:
                %s
                """.formatted(analysis.getSummary(),
                                analysis.getSkills(), 
                                analysis.getExperiences(),
                                analysis.getProjects(),
                                analysis.getEducation()
                );

        InterviewSession session = new InterviewSession();

        session.setUser(user);
        session.setResume(resume);
        session.setTitle("Mock Interview");
        session.setStatus("IN_PROGRESS");
        session.setAnsweredQuestions(0);
        session.setScore(0.0);

        InterviewSession savedSession = interviewSessionRepository
                        .save(session);

        String title = "Java Full Stack Interview";

        List<String> questions = aiService.generateInterviewQuestions(resumeText);

        int order = 1 ;

        for(String question : questions){
            InterviewQuestion iq = new InterviewQuestion();

            iq.setSession(savedSession);
            iq.setQuestionText(question);
            iq.setQuestionOrder(order++);

            interviewQuestionRepository.save(iq);
        }

        savedSession.setTotalQuestions(questions.size());

        interviewSessionRepository.save(savedSession);

        // System.out.println("Title = " + "Java Full Stack Interview");

        return StartInterviewResponse
                .builder()
                .sessionId(savedSession.getId())
                .message("Interview Started Successfully...")
                .totalQuestions(questions.size())
                .title(title)
                .build();
    }


    @Override
    public List<QuestionResponse> getQuestions(Long sessionId) {
        // TODO Auto-generated method stub
        InterviewSession session = interviewSessionRepository
                            .findById(sessionId)
                            .orElseThrow(() -> 
                                new RuntimeException(
                                    "Interview session not found"
                                ));

        List<InterviewQuestion> questions = interviewQuestionRepository
                                    .findAllBySession(session);

        return questions.stream()
            .map(question ->
                    QuestionResponse.builder()
                            .id(question.getId())
                            .questionText(question.getQuestionText())
                            .questionOrder(question.getQuestionOrder())
                            .build()
            ).toList();
    }


    @Override
    public SubmitAnswerResponse submitAnswer(Long questionId, SubmitAnswerRequest request) {

        // Fetch the question
        InterviewQuestion question = interviewQuestionRepository
                .findById(questionId)
                .orElseThrow(() ->
                        new RuntimeException("Question not found"));

        // Get the session
        InterviewSession session = question.getSession();

        // Evaluate answer using Gemini
        AnswerEvaluationResponse evaluation = aiService.evaluateAnswer(
                question.getQuestionText(),
                request.getAnswer()
        );

        double score = evaluation.getScore();
        String feedback = evaluation.getFeedback();

        // Increase answered count only if answering for the first time
        if (question.getUserAnswer() == null) {
            session.setAnsweredQuestions(session.getAnsweredQuestions() + 1);
        }

        // Save answer
        question.setUserAnswer(request.getAnswer());
        question.setScore(score);
        question.setFeedback(feedback);

        interviewQuestionRepository.save(question);

        // Calculate average score
        List<InterviewQuestion> questions =
                interviewQuestionRepository.findBySessionOrderByQuestionOrderAsc(session);

        double totalScore = questions.stream()
                .filter(q -> q.getScore() != null)
                .mapToDouble(InterviewQuestion::getScore)
                .sum();

        long answeredCount = questions.stream()
                .filter(q -> q.getScore() != null)
                .count();

        double averageScore =
                answeredCount == 0 ? 0.0 : totalScore / answeredCount;

        session.setScore(averageScore);

        interviewSessionRepository.save(session);

        // Return response
        return SubmitAnswerResponse.builder()
                .score(score)
                .feedback(feedback)
                .build();
    }

    // private double evaluateAnswer(String answer){
    //     if(answer == null || answer.isBlank()){
    //         return 0.0;
    //     }

    //     if(answer.length() > 200){
    //         return 10.0;
    //     }

    //     if(answer.length() > 100){
    //         return 8.0;
    //     }

    //     if(answer.length() > 50){
    //         return 6.0;
    //     }

    //     return 4.0;
    // }


    // private String generateFeedback(String answer){
    //     if(answer == null || answer.isBlank()){
    //         return "No answer aubmitted";
    //     }

    //     if(answer.length() > 200){
    //         return "Excellent answer with detailed explanation.";
    //     }

    //     if(answer.length() > 100){
    //         return "Average answer. More details required.";
    //     }

    //     return "Answer is too short";
    // }

    // @Override
    // public void completeInterview(Long sessionId) {
    //     // TODO Auto-generated method stub
        
    //     InterviewSession session = interviewSessionRepository
    //                                 .findById(sessionId)
    //                                 .orElseThrow(() -> 
    //                                     new RuntimeException(
    //                                         "Session not found"
    //                                     ));

    //     session.setStatus("COMPLETED");
    //     interviewSessionRepository.save(session);
    // }


    @Override
    public InterviewResultResponse getInterviewResult(Long sessionId) {
        // TODO Auto-generated method stub
        
        InterviewSession session = interviewSessionRepository
                                    .findById(sessionId)
                                    .orElseThrow( () -> 
                                        new RuntimeException(
                                            "Session not found"
                                        ));

        return InterviewResultResponse
                .builder()
                .sessionId(session.getId())
                .title(session.getTitle())
                .status(session.getStatus())
                .totalQuestions(session.getTotalQuestions())
                .answeredQuestions(session.getAnsweredQuestions())
                .score(session.getScore())
                .build();
    }


    @Override
    public List<InterviewHistoryResponse> getInterviewHistory() {
        // TODO Auto-generated method stub
        Authentication authentication = SecurityContextHolder
                                        .getContext()
                                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository
                    .findByEmail(email)
                    .orElseThrow(() -> 
                        new RuntimeException(
                            "User not found"
                        ));
        List<InterviewSession> sessions = interviewSessionRepository
                                        .findByUser(user);

        return sessions.stream()
                .map(session ->
                    InterviewHistoryResponse
                        .builder()
                        .sessionId(session.getId())
                        .title(session.getTitle())
                        .status(session.getStatus())
                        .totalQuestions(session.getTotalQuestions())
                        .answeredQuestions(session.getAnsweredQuestions())
                        .score(session.getScore())
                        .build()  
                )
                .toList();   
    }


    @Override
    public InterviewDetailResponse
    getInterviewDetails(Long sessionId) {

        InterviewSession session =
                interviewSessionRepository
                        .findById(sessionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Interview Session not found"));

        List<InterviewQuestion> questions =
                interviewQuestionRepository
                        .findBySessionOrderByQuestionOrderAsc(
                                session);

        List<InterviewQuestionDetailResponse>
                questionResponses =
                questions.stream()
                        .map(question ->
                                InterviewQuestionDetailResponse
                                        .builder()
                                        .questionId(
                                                question.getId())
                                        .questionText(
                                                question.getQuestionText())
                                        .userAnswer(
                                                question.getUserAnswer())
                                        .questionOrder(
                                                question.getQuestionOrder())
                                        .score(
                                                question.getScore())
                                        .feedback(
                                                question.getFeedback())
                                        .build())
                        .toList();

        return InterviewDetailResponse
                .builder()
                .sessionId(session.getId())
                .title(session.getTitle())
                .status(session.getStatus())
                .totalQuestions(
                        session.getTotalQuestions())
                .answeredQuestions(
                        session.getAnsweredQuestions())
                .score(session.getScore())
                .questions(questionResponses)
                .build();
    }


    @Override
    public NextQuestionResponse getNextQuestionResponse(Long sesionId) {
        // TODO Auto-generated method stub
        
        InterviewSession session = interviewSessionRepository
                    .findById(sesionId)
                    .orElseThrow( () ->
                        new RuntimeException("Interview session not found")
                    );

        List<InterviewQuestion> questions = 
                interviewQuestionRepository.findBySessionOrderByQuestionOrderAsc(session);

        
        InterviewQuestion nextQuestion = questions.stream()
                .filter( q -> q.getUserAnswer() == null || q.getUserAnswer().isBlank())
                .findFirst()
                .orElseThrow( () ->
                    new RuntimeException("Interview completed")
                );

        return NextQuestionResponse.builder()
                .questionId(nextQuestion.getId())
                .questionOrder(nextQuestion.getQuestionOrder()) 
                .questionText(nextQuestion.getQuestionText())
                .build();       

    }


    @Override
    public InterviewSummaryResponse compeleteInterview(Long sessionId) {
        // TODO Auto-generated method stub
        
        InterviewSession session = interviewSessionRepository
                    .findById(sessionId)
                    .orElseThrow( () ->
                        new RuntimeException("Intterview Session not Found..")
                    );


        if(!session.getAnsweredQuestions().equals(session.getTotalQuestions())){
            throw new RuntimeException("Interview is not completed yet.");
        }

        session.setStatus("COMPLETED");

        interviewSessionRepository.save(session);

        return InterviewSummaryResponse.builder()
                .sessionId(session.getId())
                .status(session.getStatus())
                .totalQuestions(session.getTotalQuestions())
                .answeredQuestions(session.getAnsweredQuestions())
                .averageScore(session.getScore())
                .message("Interview Completed successfully")
                .build();

    }
   
}
