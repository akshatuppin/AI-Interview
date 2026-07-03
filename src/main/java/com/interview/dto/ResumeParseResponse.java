package com.interview.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({
    "resumeId",
    "extractedText"
})
public class ResumeParseResponse {
    
    private Long resumeId;

    private String extractedText;

}
