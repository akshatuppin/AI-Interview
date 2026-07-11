package com.interview.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interview.entity.Resume;
import com.interview.entity.ResumeAnalysis;



public interface ResumeAnalysisRepository extends JpaRepository<ResumeAnalysis, Long>{
    
    Optional<ResumeAnalysis> findByResume(Resume resume);
}
