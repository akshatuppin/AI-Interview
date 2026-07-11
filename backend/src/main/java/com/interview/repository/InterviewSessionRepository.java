package com.interview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interview.entity.InterviewSession;
import com.interview.entity.User;
import com.interview.enums.InterviewStatus;

@Repository
public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long>{
    List<InterviewSession> findAllByUser(User user);

    List<InterviewSession> findByUser(User user);

    long countByUserId(Long userId);

    long countByUserIdAndStatus(Long userId, InterviewStatus status);

    List<InterviewSession> findByUserId(Long userId);

    InterviewSession findTopByUserIdOrderByCreatedAtDesc(Long userId);

}
