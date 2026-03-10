package com.example.devtrack.goal.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateGoalRequest(

        @NotBlank
        String title,

        @NotBlank
        String description
) {}
