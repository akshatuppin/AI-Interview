package com.interview.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumeSummaryResponse {
    
    private Long resumeId;

    private String fileName;

}
