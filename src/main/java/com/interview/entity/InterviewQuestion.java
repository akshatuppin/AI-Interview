package com.interview.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "interview_questions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class InterviewQuestion extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(columnDefinition = "TEXT")
    @Lob
    private String questionText;

    private Integer questionOrder;

    @Column(columnDefinition = "TEXT")
    private String userAnswer;

    private Double score;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private InterviewSession session;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String feedback;
}
