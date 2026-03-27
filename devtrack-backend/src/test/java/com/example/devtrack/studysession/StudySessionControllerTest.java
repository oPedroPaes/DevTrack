package com.example.devtrack.studysession;

import com.example.devtrack.exception.SessionNotFoundException;
import com.example.devtrack.security.CustomUserDetailsService;
import com.example.devtrack.security.JwtService;
import com.example.devtrack.studysession.dto.StudySessionResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudySessionController.class)
class StudySessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudySessionService studySessionService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Nested
    class CreateSession {
        @Test
        void shouldCreateSessionSuccessfully () throws Exception {

            UUID sessionId = UUID.randomUUID();

            StudySessionResponse response = new StudySessionResponse(
                    sessionId,
                    null,
                    0,
                    "Test",
                    null
            );

            when(studySessionService.create(any(),eq("test@email.com")))
                    .thenReturn(response);

            String json = """
                    {
                        "subject": "Test",
                        "goalId": null
                    }
                    """;

            mockMvc.perform(
                    post("/sessions")
                            .contentType("application/json")
                            .content(json)
                            .with(user("test@email.com"))
                            .with(csrf())
            )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.subject").value("Test"));
        }
    }

    @Nested
    class FinishSession {

        @Test
        void shouldFinishSessionSuccessfully() throws Exception {

            UUID sessionId = UUID.randomUUID();

            StudySessionResponse response = new StudySessionResponse(
                    sessionId,
                    null,
                    60,
                    "Test",
                    null
            );

            when(studySessionService.finish(sessionId, "test@email.com"))
                    .thenReturn(response);

            mockMvc.perform(
                            patch("/sessions/" + sessionId + "/finish")
                                    .with(user("test@email.com"))
                                    .with(csrf())
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.subject").value("Test"));

            verify(studySessionService)
                    .finish(eq(sessionId), eq("test@email.com"));
        }

        @Test
        void shouldReturn404WhenSessionNotFound() throws Exception {

            UUID sessionId = UUID.randomUUID();

            when(studySessionService.finish(any(UUID.class), any(String.class)))
                    .thenThrow(new SessionNotFoundException("Session not found"));

            mockMvc.perform(
                            patch("/sessions/" + sessionId + "/finish")
                                    .with(user("test@email.com"))
                                    .with(csrf())
                    )
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("Session not found"));
        }

        @Test
        void shouldReturn401WhenUserNotAuthenticated() throws Exception {

            UUID sessionId = UUID.randomUUID();

            mockMvc.perform(
                            patch("/sessions/" + sessionId + "/finish")
                                    .with(csrf())
                    )
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class ListSessions {

        @Test
        void shouldReturnUserSessions() throws Exception {

            StudySessionResponse session = new StudySessionResponse(
                    UUID.randomUUID(),
                    null,
                    60,
                    "Test",
                    null
            );

            when(studySessionService.listMySessions("test@email.com"))
                    .thenReturn(List.of(session));

            mockMvc.perform(
                    get("/sessions")
                            .with(user("test@email.com"))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].subject").value("Test"));
        }
    }
}
