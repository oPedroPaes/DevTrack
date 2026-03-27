package com.example.devtrack.studysession.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateStudySessionRequest(

        @NotBlank(message = "Subject is required")
        String subject,

        UUID goalId
) {}
