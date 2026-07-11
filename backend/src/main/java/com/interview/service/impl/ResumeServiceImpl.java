package com.interview.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.interview.dto.ResumeAnalysisResponse;
import com.interview.dto.ResumeParseResponse;
import com.interview.dto.ResumeSummaryResponse;
import com.interview.dto.ResumeUploadResponse;
import com.interview.entity.ParsedResume;
import com.interview.entity.Resume;
import com.interview.entity.ResumeAnalysis;
import com.interview.entity.User;
import com.interview.repository.ParsedResumeRepository;
import com.interview.repository.ResumeAnalysisRepository;
import com.interview.repository.ResumeRepository;
import com.interview.repository.UserRepository;
import com.interview.service.ResumeService;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import lombok.RequiredArgsConstructor;

import org.apache.pdfbox.text.PDFTextStripper;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService{
    
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository ;
    private final ParsedResumeRepository parsedResumeRepository;
    private final ResumeAnalysisRepository resumeAnalysisRepository;


    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ResumeUploadResponse uploadResponse(MultipartFile file) {
        // TODO Auto-generated method stub
        // if(!file.getContentType().equals("application/pdf")){
        //     throw new RuntimeException("Only PDF files allowed");
        // }

        // String fileName =  UUID.randomUUID()+ "_" + file.getOriginalFilename();

        try{
            if(file == null || file.isEmpty()){
                throw new RuntimeException("Please Select a file");
            }

            if(!"application/pdf".equals(file.getContentType())){
                throw new RuntimeException("Only PDF files allowed");
            }

            Authentication authentication = SecurityContextHolder
                                    .getContext()
                                    .getAuthentication();
            
            String email = authentication.getName();

            User user = userRepository.findByEmail(email)
                            .orElseThrow(() -> 
                                new RuntimeException(
                                    "User not Found"));

            Path uploadPath =
                    Paths.get(uploadDir); 
            
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            Resume resume = new Resume();

            resume.setUser(user);
            resume.setFileName(fileName);
            resume.setFilePath(filePath.toString());
            resume.setFileType(file.getContentType());
            resume.setFileSize(file.getSize());

            Resume savedResume = resumeRepository.save(resume);


            return ResumeUploadResponse.builder()
                                        .resumeId(savedResume.getId())
                                        .fileName(savedResume.getFileName())
                                        .message("Resume uploaded successfully")
                                        .build();
        }catch(IOException e){
            throw new RuntimeException("Failed to upload resume", e);
        }
    }

    @Override
    public ResumeParseResponse parseResume(
            Long resumeId) {

        try {

            Resume resume =
                    resumeRepository.findById(resumeId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Resume not found"));

            File file =
                    new File(resume.getFilePath());

            PDDocument document =
                    Loader.loadPDF(file);

            PDFTextStripper stripper =
                    new PDFTextStripper();

            String extractedText =
                    stripper.getText(document);

            document.close();

            ParsedResume parsedResume =
                    parsedResumeRepository
                            .findByResume(resume)
                            .orElse(new ParsedResume());

            parsedResume.setResume(resume);
            parsedResume.setExtractedText(
                    extractedText);

            parsedResumeRepository.save(
                    parsedResume);

            return ResumeParseResponse
                    .builder()
                    .resumeId(resumeId)
                    .extractedText(extractedText)
                    .build();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to parse resume",
                    e);
        }
    }

    @Override
    public List<ResumeSummaryResponse> getMyResumes() {
        // TODO Auto-generated method stub
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("User not Found")
                        );

        List<Resume> resumes = resumeRepository.findAllByUser(user);

        return resumes.stream()
                .map(resume -> 
                        ResumeSummaryResponse
                                .builder()
                                .resumeId(resume.getId())
                                .fileName(resume.getFileName())
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public ResumeAnalysisResponse analysisResume(Long resumeId) {
        // TODO Auto-generated method stub
        Resume resume = resumeRepository.findById(resumeId)
                        .orElseThrow(() -> 
                        new RuntimeException(
                                "Resume not found"
                        ));

        ParsedResume parsedResume = 
                parsedResumeRepository
                        .findByResume(resume)
                        .orElseThrow( () -> 
                                new RuntimeException("Resume not parsed yet"));

        String text = parsedResume.getExtractedText();

        System.out.println(
                "Analysis Exists: " +
                resumeAnalysisRepository
                        .findByResume(resume)
                        .isPresent()
                );

        ResumeAnalysis analysis = resumeAnalysisRepository
                                .findByResume(resume)
                                .orElse(new ResumeAnalysis());

        

        analysis.setResume(resume);

        String lowerText = text.toLowerCase();

        String skills = "";

        if(lowerText.contains("java"))
                skills += "Java, ";

        if(lowerText.contains("spring"))
                skills += "Spring Boot, ";

        if(lowerText.contains("mysql"))
                skills += "MySQL, ";

         if(lowerText.contains("react"))
                skills += "React, ";

        if(lowerText.contains("mongodb"))
                skills += "MongoDB, ";     
                
        String projects = "";

        if(lowerText.contains("library"))
                projects += "Library Management System, ";

        if(lowerText.contains("interview"))
                projects += "AI Interview Platform, ";
        
        String education = "";

        if(lowerText.contains("b.e"))
                education = "Bachelor of Engineering";

        if(lowerText.contains("btech"))
                education = "Bachelor of Technology";
    
        String experience = "";

        if(lowerText.contains("intern"))
                experience = "Internship Experience Found";
    
        analysis.setSkills(skills);
        analysis.setProjects(projects);
        analysis.setEducation(education);
        analysis.setExperiences(experience);
        analysis.setSummary(text);


        ResumeAnalysis savedAnalysis = 
                resumeAnalysisRepository
                        .save(analysis);


        return ResumeAnalysisResponse
                .builder()
                .id(resumeId)
                .skills(savedAnalysis.getSkills())
                .projects(savedAnalysis.getProjects())
                .education(savedAnalysis.getEducation())
                .experience(savedAnalysis.getExperiences())
                .build();
   }

}
