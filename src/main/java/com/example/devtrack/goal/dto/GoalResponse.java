package com.example.devtrack.goal.dto;

import com.example.devtrack.goal.Goal;

import java.time.LocalDate;
import java.util.UUID;

public record GoalResponse(
        UUID id,
        String title,
        String description,
        LocalDate targetDate,
        Goal.GoalStatus status
) {}
