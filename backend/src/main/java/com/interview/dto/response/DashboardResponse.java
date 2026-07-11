package com.interview.dto.response;

import com.interview.dto.InterviewHistoryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {
    
    private long totalInterviews;

    private long completedInterviews;

    private long inProgressInterviews;

    private Double averageScore;

    private InterviewHistoryResponse recentInterview;

}
