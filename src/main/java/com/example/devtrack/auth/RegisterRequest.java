package com.example.devtrack.auth;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = " Email is required")
        String email,

        @NotBlank(message = "Password is required")
        String password
) {}