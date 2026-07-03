package com.interview.dto.gemini;

import java.util.List;

import lombok.Data;

@Data
public class ResponseContent {
    
    private List<ResponsePart> parts;
}
