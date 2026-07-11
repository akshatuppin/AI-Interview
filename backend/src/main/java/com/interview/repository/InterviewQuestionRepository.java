package com.interview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interview.entity.InterviewQuestion;
import com.interview.entity.InterviewSession;

@Repository
public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long>{

    List<InterviewQuestion> findAllBySession(InterviewSession session);
    
    List<InterviewQuestion> findBySessionOrderByQuestionOrderAsc(InterviewSession session);
} 
