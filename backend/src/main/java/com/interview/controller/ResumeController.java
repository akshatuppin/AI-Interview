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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;




@Tag(
    name = "Resume Management",
    description = "APIs for uploading, parsing and analyzing resumes"
)
@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {
    

    private final ResumeService resumeService;


    @Operation(
        summary = "Upload Resume",
        description = "Uploades a PDF resume for the authenticated user."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resume uploaded successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid file format"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/upload")
    public ResponseEntity<ResumeUploadResponse> uploadResume(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(resumeService.uploadResponse(file));
    }

    @Operation(
        summary = "Parse Resume",
        description = "Extracts text and structured information from the uploaded resume."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resume parsed successfully"),
        @ApiResponse(responseCode = "400", description = "Resume not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/{resumeId}/parse")
    public ResponseEntity<ResumeParseResponse>parseResume(@PathVariable Long resumeId){
        return ResponseEntity.ok(resumeService.parseResume(resumeId));
    }
    
    @Operation(
        summary = "Returns the users current Resume",
        description = "Used to see the current Resume"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "The current resume found"),
        @ApiResponse(responseCode = "400", description = "Resume musst not be present"),
        @ApiResponse(responseCode = "401", description = "Resume not found")
    })

    @GetMapping("/my-resumes")
    public ResponseEntity<List<ResumeSummaryResponse>> getMyResumes() {
        return ResponseEntity.ok(resumeService.getMyResumes());
    }
    

    @Operation(
        summary = "Analyze Resume",
        description = "Uses AI to analyze the parded resume and generate skills, projects, and summary."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resume analyzed successfully"),
        @ApiResponse(responseCode = "400", description = "Resume must be parsed first"),
        @ApiResponse(responseCode = "404", description = "Resume not found")
    })
    @PostMapping("/{resumeId}/analyze")
    public ResponseEntity<ResumeAnalysisResponse> analyzeResume(@PathVariable Long resumeId){
        return ResponseEntity.ok(resumeService.analysisResume(resumeId));
    }
    
}
