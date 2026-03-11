package com.example.devtrack.dashboard;

import com.example.devtrack.studysession.StudySessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final StudySessionRepository sessionRepository;


    public DashboardResponse getStats(UUID userId) {

        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);

        Integer today = sessionRepository.getTodayStudyTime(userId);
        Integer week = sessionRepository.getWeekStudyTime(userId,  sevenDaysAgo);
        Integer month = sessionRepository.getMonthStudyTime(userId);

        return new DashboardResponse(today, week, month);
    }

}
