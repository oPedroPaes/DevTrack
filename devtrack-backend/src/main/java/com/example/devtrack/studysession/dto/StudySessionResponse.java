package com.example.devtrack.studysession.dto;

import java.time.LocalDate;
import java.util.UUID;

public record StudySessionResponse(
        UUID id,
        LocalDate date,
        Integer durationInMinutes,
        String subject,
        UUID goalID
) {}