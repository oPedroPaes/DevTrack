package com.example.devtrack.goal.dto;

import com.example.devtrack.goal.Goal;
import jakarta.validation.constraints.NotNull;

public record UpdateGoalStatusRequest(
        @NotNull
        Goal.GoalStatus status
) {}
