package com.example.devtrack.studysession;

import com.example.devtrack.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudySessionRepository extends JpaRepository<StudySession, UUID> {

    List<StudySession> findByUser(User user);

    Optional<StudySession> findByIdAndUser(UUID id, User user);

    @Query("""
            SELECT COALESCE(SUM(s.durationInMinutes), 0)
            FROM StudySession s
            WHERE s.user.id = :userId
            AND s.date = CURRENT_DATE
            """)
    Integer getTodayStudyTime(UUID userId);

    @Query("""
        SELECT COALESCE(SUM(s.durationInMinutes), 0)
        FROM StudySession s
        WHERE s.user.id = :userId
        AND s.date >= :startDate
""")
    Integer getWeekStudyTime(UUID userId, LocalDate startDate);

    @Query("""
        SELECT COALESCE(SUM(s.durationInMinutes), 0)
        FROM StudySession s
        WHERE s.user.id = :userId
        AND EXTRACT(MONTH FROM s.date) = EXTRACT(MONTH FROM CURRENT_DATE )
        AND EXTRACT(YEAR FROM s.date) = EXTRACT(YEAR FROM CURRENT_DATE )
""")
    Integer getMonthStudyTime(UUID userId);

    @Query("""
        SELECT COALESCE(SUM(s.durationInMinutes), 0) / 7
        FROM StudySession s
        WHERE s.user.id = :userId
        AND s.date >= :startDate
""")
    Integer getWeeklyAverage(UUID userId, LocalDate startDate);

    @Query("""
        SELECT COALESCE(SUM(s.durationInMinutes), 0) / EXTRACT(DAY FROM CURRENT_DATE)
        FROM StudySession s
        WHERE s.user.id = :userId
        AND EXTRACT(MONTH FROM s.date) = EXTRACT(MONTH FROM CURRENT_DATE)
        AND EXTRACT(YEAR FROM s.date) = EXTRACT(YEAR FROM CURRENT_DATE)
""")
    Integer getMonthlyAverage(UUID userId);
}
