package com.interview.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.interview.dto.ai.AnswerEvaluationResponse;
import com.interview.dto.gemini.Content;
import com.interview.dto.gemini.GeminiRequest;
import com.interview.dto.gemini.GeminiResponse;
import com.interview.dto.gemini.Part;
import com.interview.service.AIService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService{

    private final WebClient webClient;


    @Value("${gemini.api.key}")
    private String apiKey;



    @Value("${gemini.api.url}")
    private String apiUrl;

    @Override
    public List<String> generateInterviewQuestions(String resumeText) {
        
        String prompt = """
                Generate exactly 5 techinical interview questions based on the following resume.

                Resume:
                %s

                Return only the questions.
                One question per line.
                Do not use numbering.

                """.formatted(resumeText);



                GeminiRequest request = new GeminiRequest(
                    List.of(
                        new Content(
                            List.of(
                                new Part(prompt)
                            )
                        )
                    )
                );

                
                GeminiResponse response = webClient.post()
                        .uri(apiUrl + "?key=" + apiKey)
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(GeminiResponse.class)
                        .block();

                if(response == null
                    || response.getCandidates() == null
                    || response.getCandidates().isEmpty()){

                    throw new RuntimeException("No response recived from Gemini..");
                }

                String text = response.getCandidates()
                        .get(0)
                        .getContent()
                        .getParts()
                        .get(0)
                        .getText();

                return Arrays.stream(text.split("\\n"))
                        .map(String::trim)
                        .filter(s -> !s.isBlank())
                        .toList();






        
        // Map<String, Object> body = Map.of(
        //     "contents", List.of(
        //         Map.of(
        //             "parts",
        //             List.of(
        //                 Map.of("text", prompt)
        //             )
        //         )
        //     )
        // );
        
        // // String response = webClient.post()
        // //             .uri(apiUrl + "?key=" + apiKey)
        // //             .bodyValue(body)
        // //             .retrieve()
        // //             .bodyToMono(String.class)
        // //             .block();

        // System.out.println("API key = " + apiKey);

        // String response = webClient.post()
        //     .uri(apiUrl + "?key=" + apiKey)
        //     .header("Content-Type", "application/json")
        //     .bodyValue(body)
        //     .retrieve()
        //     .onStatus(
        //             status -> status.isError(),
        //             clientResponse -> clientResponse.bodyToMono(String.class)
        //                     .map(error -> new RuntimeException(error))
        //     )
        //     .bodyToMono(String.class)
        //     .block();
        
       

        // return Arrays.asList(response.split("\n\n"));
    }

    @Override
    public AnswerEvaluationResponse evaluateAnswer(String question, String answer) {
        // TODO Auto-generated method stub

        System.out.println(apiKey);
        
        String promt = """
                You are an experienced technical interviewer.

                Evaluate the candidate's answer.

                Question:
                %s

                Candidate Answer:
                %s

                Give:
                Score (0-10)
                FeedBack

                Return ONLY in this format:

                Score: <number>
                Feedback: <feedback>
                """.formatted(question, answer);
        
        GeminiRequest request = new GeminiRequest(
            List.of(
                new Content(
                    List.of(
                        new Part(promt)
                    )
                )
            )
        );

        // GeminiResponse response = webClient.post()
        //     .uri(apiUrl + "?key=" + apiKey)
        //     .bodyValue(request)
        //     .retrieve()
        //     .bodyToMono(GeminiResponse.class)
        //     .block();

        GeminiResponse response = webClient.post()
        .uri(apiUrl + "?key=" + apiKey)
        .bodyValue(request)
        .retrieve()
        .onStatus(
                status -> status.isError(),
                clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .map(error -> {
                                    System.out.println(error);
                                    return new RuntimeException(error);
                                })
        )
        .bodyToMono(GeminiResponse.class)
        .block();

        String text = response.getCandidates()
                .get(0)
                .getContent()
                .getParts()
                .get(0)
                .getText();

        System.out.println(promt);
        System.out.println(request);
        // System.out.println(apiKey);

        return parseEvaluation(text);
    }

    private AnswerEvaluationResponse parseEvaluation(String text){
        double score = 0.0;

        String feedback = "";

        String[] lines = text.split("\\n");

        for(String line : lines){
            if(line.startsWith("Score:")){
                score = Double.parseDouble(
                    line.replace("Score:", "").trim()
                );
            }else if(line.startsWith("Feedback:")){
                feedback = line.replace("Feedback:", "").trim();
            }
        }

        return new AnswerEvaluationResponse(score, feedback);
    }
    
}
