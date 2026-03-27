package com.example.devtrack.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(

        @NotBlank
        String name
) {}
