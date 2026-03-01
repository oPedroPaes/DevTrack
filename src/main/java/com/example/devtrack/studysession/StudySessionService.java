package com.example.devtrack.studysession;


import com.example.devtrack.goal.Goal;
import com.example.devtrack.goal.GoalRepository;
import com.example.devtrack.studysession.dto.CreateStudySessionRequest;
import com.example.devtrack.studysession.dto.StudySessionResponse;
import com.example.devtrack.user.User;
import com.example.devtrack.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudySessionService {

    private final StudySessionRepository studySessionRepository;
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;

    public StudySessionResponse create(CreateStudySessionRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudySession session = new StudySession();
        session.setDate(LocalDate.now());
        session.setStartTime(LocalDateTime.now());
        session.setEndTime(null);
        session.setDurationInMinutes(0);
        session.setSubject(request.subject());
        session.setUser(user);

        if (request.goalId() != null) {
            Goal goal = goalRepository
                    .findByIdAndUser(request.goalId(),user)
                    .orElseThrow(() -> new RuntimeException("Goal not found"));
            session.setGoal(goal);
        }

        StudySession saved = studySessionRepository.save(session);

        return new StudySessionResponse(
                saved.getId(),
                saved.getDate(),
                saved.getDurationInMinutes(),
                saved.getSubject(),
                saved.getGoal() != null ? saved.getGoal().getId() : null
        );
    }

    public List<StudySessionResponse> listMySessions(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return studySessionRepository.findByUser(user)
                .stream()
                .map(session -> new StudySessionResponse(
                        session.getId(),
                        session.getDate(),
                        session.getDurationInMinutes(),
                        session.getSubject(),
                        session.getGoal() != null ? session.getGoal().getId() : null
                ))
                .toList();
    }

    public StudySessionResponse finish(UUID sessionId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudySession session = studySessionRepository
                .findByIdAndUser(sessionId, user)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (session.getEndTime() != null) {
            throw new RuntimeException("Session already finished");
        }

        LocalDateTime endTime = LocalDateTime.now();
        session.setEndTime(endTime);

        long duration = Duration.between(
                session.getStartTime(),
                endTime
        ).toMinutes();

        session.setDurationInMinutes((int) duration);

        StudySession saved = studySessionRepository.save(session);

        return mapToResponse(saved);
    }

    private StudySessionResponse mapToResponse(StudySession session) {
        return new StudySessionResponse(
                session.getId(),
                session.getDate(),
                session.getDurationInMinutes(),
                session.getSubject(),
                session.getGoal() != null ? session.getGoal().getId() : null
        );
    }
}
