package com.example.devtrack.studysession;

import com.example.devtrack.exception.SessionNotFoundException;
import com.example.devtrack.exception.UserNotFoundException;
import com.example.devtrack.user.User;
import com.example.devtrack.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudySessionServiceTest {

    @Mock
    private StudySessionRepository studySessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StudySessionService studySessionService;

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
    void shouldFinishSessionSuccessfully() {
        UUID sessionId = UUID.randomUUID();
        String email = "test@email.com";

        User user = new User();
        user.setEmail(email);

        StudySession session = new StudySession();
        session.setUser(user);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(studySessionRepository.findByIdAndUser(sessionId, user))
                .thenReturn(Optional.of(session));

        studySessionService.finish(sessionId, email);

        verify(studySessionRepository).save(session);
    }


}