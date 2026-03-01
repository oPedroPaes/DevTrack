package com.example.devtrack.studysession;

import com.example.devtrack.user.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudySessionRepository extends JpaRepository<StudySession, UUID> {

    List<StudySession> findByUser(User user);

    Optional<StudySession> findByIdAndUser(UUID id, User user);
}
