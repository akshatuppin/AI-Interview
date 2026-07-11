package com.interview.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumeUploadResponse {
    
    private Long resumeId;

    private String fileName;

    private String message;

}
