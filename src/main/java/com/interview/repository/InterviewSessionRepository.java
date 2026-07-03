package com.interview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interview.dto.InterviewDetailResponse;
import com.interview.entity.InterviewSession;
import com.interview.entity.User;

@Repository
public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long>{
    List<InterviewSession> findAllByUser(User user);

    List<InterviewSession> findByUser(User user);

    // InterviewDetailResponse getInterviewDetails(Long sessionId);
}
