package com.interview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interview.entity.Resume;
import com.interview.entity.User;

public interface ResumeRepository extends JpaRepository<Resume, Long>{
    
    List<Resume> findAllByUser(User user);

    // boolean existsByUser(User user);
}
