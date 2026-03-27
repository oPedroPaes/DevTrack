package com.example.devtrack.studysession.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateSessionRequest(

        @NotBlank
        String subject
) {}
