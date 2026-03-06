package com.example.devtrack.studysession;

import com.example.devtrack.exception.GoalNotFoundException;
import com.example.devtrack.exception.SessionAlreadyFinishedException;
import com.example.devtrack.exception.SessionNotFoundException;
import com.example.devtrack.exception.UserNotFoundException;
import com.example.devtrack.goal.Goal;
import com.example.devtrack.goal.GoalRepository;
import com.example.devtrack.studysession.dto.CreateStudySessionRequest;
import com.example.devtrack.studysession.dto.StudySessionResponse;
import com.example.devtrack.user.User;
import com.example.devtrack.user.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudySessionServiceTest {

    @Mock
    private StudySessionRepository studySessionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private StudySessionService studySessionService;

    @Nested
    class FinishSession {
        @Test
        void shouldFinishSessionSuccessfully() {

            UUID sessionId = UUID.randomUUID();
            String email = "test@email.com";

            User user = new User();
            user.setEmail(email);

            StudySession session = new StudySession();
            session.setUser(user);
            session.setStartTime(LocalDateTime.now().minusHours(1));

            when(userRepository.findByEmail(email))
                    .thenReturn(Optional.of(user));

            when(studySessionRepository.findByIdAndUser(sessionId, user))
                    .thenReturn(Optional.of(session));

            when(studySessionRepository.save(any()))
                    .thenReturn(session);

            studySessionService.finish(sessionId, email);

            verify(studySessionRepository).save(session);

            assertNotNull(session.getEndTime());
            assertTrue(session.getDurationInMinutes() > 0);
        }

        @Test
        void shouldThrowSessionNotFoundWhenSessionDoesNotExist() {

            UUID sessionId = UUID.randomUUID();
            String email = "test@email.com";

            User user = new User();
            user.setEmail(email);

            when(userRepository.findByEmail(email))
                    .thenReturn(Optional.of(user));

            when(studySessionRepository.findByIdAndUser(sessionId, user))
                    .thenReturn(Optional.empty());

            assertThrows(SessionNotFoundException.class, () -> studySessionService.finish(sessionId, email));
        }

        @Test
        void shouldThrowUserNotFoundWhenUserNotFound() {
            UUID sessionId = UUID.randomUUID();
            String email = "test@email.com";

            when(userRepository.findByEmail(email))
                    .thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () ->
                    studySessionService.finish(sessionId, email)
            );
        }

        @Test
        void shouldThrowSessionAlreadyFinishedWhenSessionIsFinished() {

            UUID sessionId = UUID.randomUUID();
            String email = "test@email.com";

            User user = new User();
            user.setEmail(email);

            StudySession session = new StudySession();
            session.setUser(user);
            session.setStartTime(LocalDateTime.now().minusHours(1));
            session.setEndTime(LocalDateTime.now());

            when(userRepository.findByEmail(email))
                    .thenReturn(Optional.of(user));

            when(studySessionRepository.findByIdAndUser(sessionId, user))
                    .thenReturn(Optional.of(session));

            assertThrows(SessionAlreadyFinishedException.class,
                    () -> studySessionService.finish(sessionId, email));

            verify(studySessionRepository, never()).save(any());
        }
    }

    @Nested
    class CreateSession {

        @Test
        void shouldCreateSessionWithoutGoal() {

            String email = "test@email.com";

            User user = new User();
            user.setEmail(email);

            CreateStudySessionRequest request = new CreateStudySessionRequest(
                    "TestSubject",
                    null
            );

            StudySession savedSession = new StudySession();
            savedSession.setId(UUID.randomUUID());
            savedSession.setUser(user);
            savedSession.setSubject("TestSubject");
            savedSession.setDurationInMinutes(0);

            when(userRepository.findByEmail(email))
                    .thenReturn(Optional.of(user));

            when(studySessionRepository.save(any()))
                    .thenReturn(savedSession);

            StudySessionResponse response = studySessionService.create(
                    request,
                    email
            );

            verify(studySessionRepository).save(any());

            assertEquals("TestSubject", response.subject());
            assertEquals(0, response.durationInMinutes());
        }

        @Test
        void shouldCreateSessionWithGoal() {

            String email = "test@email.com";

            Goal goal = new Goal();
            goal.setId(UUID.randomUUID());

            User user = new User();
            user.setEmail(email);

            CreateStudySessionRequest request = new CreateStudySessionRequest(
                    "TestSubject",
                    goal.getId()
            );

            StudySession savedSession = new StudySession();
            savedSession.setId(UUID.randomUUID());
            savedSession.setUser(user);
            savedSession.setSubject("TestSubject");
            savedSession.setDurationInMinutes(0);
            savedSession.setGoal(goal);

            when(userRepository.findByEmail(email))
                    .thenReturn(Optional.of(user));

            when(goalRepository.findByIdAndUser(goal.getId(), user))
                    .thenReturn(Optional.of(goal));

            when(studySessionRepository.save(any()))
                    .thenReturn(savedSession);

            StudySessionResponse response = studySessionService.create(
                    request,
                    email
            );

            verify(studySessionRepository).save(any());

            assertEquals("TestSubject", response.subject());
            assertEquals(0, response.durationInMinutes());
            assertEquals(goal.getId(), response.goalID());
        }

        @Test
        void shouldThrowUserNotFoundWhenUserNotFound () {

            String email = "test@email.com";

            CreateStudySessionRequest request = new CreateStudySessionRequest(
                    "TestSubject",
                    null
            );

            when(userRepository.findByEmail(email))
                    .thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class,
                    () -> studySessionService.create(request,email));

            verify(studySessionRepository, never()).save(any());
        }

        @Test
        void shouldThrowGoalNotFoundWhenGoalNotFound () {

            String email = "test@email.com";
            UUID goalId = UUID.randomUUID();

            User user = new User();
            user.setEmail(email);

            CreateStudySessionRequest request = new CreateStudySessionRequest(
                    "TestSubject",
                    goalId
            );

            when(userRepository.findByEmail(email))
                    .thenReturn(Optional.of(user));

            when(goalRepository.findByIdAndUser(goalId, user))
                    .thenReturn(Optional.empty());

            assertThrows(GoalNotFoundException.class,
                    () -> studySessionService.create(request, email));

            verify(studySessionRepository, never()).save(any());
        }
    }

    @Nested
    class ListMySessions {

        @Test
        void shouldReturnUserSessions () {

            String email = "test@email.com";

            User user = new User();
            user.setEmail(email);

            when(userRepository.findByEmail(email))
                    .thenReturn(Optional.of(user));

            when(studySessionRepository.findByUser(user))
                    .thenReturn(List.of(new StudySession(), new StudySession()));

            var result = studySessionService.listMySessions(email);

            assertEquals(2, result.size());
        }

        @Test
        void shouldThrowUserNotFoundWhenUserNotFound () {

            String email = "test@email.com";

            when(userRepository.findByEmail(email))
                    .thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class,
                    () -> studySessionService.listMySessions(email));
        }
    }
}