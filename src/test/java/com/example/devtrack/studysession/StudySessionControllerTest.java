package com.example.devtrack.studysession;

import com.example.devtrack.exception.SessionNotFoundException;
import com.example.devtrack.security.CustomUserDetailsService;
import com.example.devtrack.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

    @Test
    void shouldFinishSessionSuccessfully() throws Exception {

        UUID sessionId = UUID.randomUUID();

        mockMvc.perform(
                patch("/sessions/" + sessionId + "/finish")
                        .with(user("test@email.com"))
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }
}
