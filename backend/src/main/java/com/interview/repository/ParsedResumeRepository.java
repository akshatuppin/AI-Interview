package com.interview.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interview.entity.ParsedResume;
import com.interview.entity.Resume;




public interface ParsedResumeRepository extends JpaRepository<ParsedResume, Long>{

    Optional<ParsedResume> findByResume(Resume resume);

}