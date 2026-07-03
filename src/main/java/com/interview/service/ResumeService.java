package com.interview.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.interview.dto.ResumeAnalysisResponse;
import com.interview.dto.ResumeParseResponse;
import com.interview.dto.ResumeSummaryResponse;
import com.interview.dto.ResumeUploadResponse;

public interface ResumeService {

    ResumeUploadResponse uploadResponse( MultipartFile file);

    ResumeParseResponse parseResume(Long resumeId);

    List<ResumeSummaryResponse> getMyResumes();

    ResumeAnalysisResponse analysisResume(Long resumeId);

}
