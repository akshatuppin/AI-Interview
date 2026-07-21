import { InterviewHistoryResponse } from "./InterviewHistoryResponse";

export interface DashboardResponse{
    totalInterviews: number;

    completedInterviews: number;

    inProgressInterviews: number;

    averageScore: number;

    recentInterview: InterviewHistoryResponse;
}