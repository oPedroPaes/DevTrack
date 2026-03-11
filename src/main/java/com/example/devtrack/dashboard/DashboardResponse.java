package com.example.devtrack.dashboard;

public record DashboardResponse(
        Integer todayMinutes,
        Integer weekMinutes,
        Integer monthMinutes
) {}
