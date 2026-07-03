package com.interview.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interview.dto.ResumeAnalysisResponse;
import com.interview.dto.ResumeParseResponse;
import com.interview.dto.ResumeSummaryResponse;
import com.interview.dto.ResumeUploadResponse;
import com.interview.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {
    

    private final ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<ResumeUploadResponse> uploadResume(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(resumeService.uploadResponse(file));
    }

    @PostMapping("/{resumeId}/parse")
    public ResponseEntity<ResumeParseResponse>parseResume(@PathVariable Long resumeId){
        return ResponseEntity.ok(resumeService.parseResume(resumeId));
    }
    

    @GetMapping("/my-resumes")
    public ResponseEntity<List<ResumeSummaryResponse>> getMyResumes() {
        return ResponseEntity.ok(resumeService.getMyResumes());
    }
    
    @PostMapping("/{resumeId}/analyze")
    public ResponseEntity<ResumeAnalysisResponse> analyzeResume(@PathVariable Long resumeId){
        return ResponseEntity.ok(resumeService.analysisResume(resumeId));
    }
    
}
