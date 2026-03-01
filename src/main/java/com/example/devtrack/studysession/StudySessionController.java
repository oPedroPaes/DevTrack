package com.example.devtrack.studysession;


import com.example.devtrack.studysession.dto.CreateStudySessionRequest;
import com.example.devtrack.studysession.dto.StudySessionResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class StudySessionController {

    private final StudySessionService studySessionService;

    @PostMapping
    public ResponseEntity<StudySessionResponse> create(
            @RequestBody CreateStudySessionRequest request,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal
            ) {

        StudySessionResponse response = studySessionService.create(
                request, principal.getUsername()
        );

        return ResponseEntity.status(201).body(response);
    }

    @PatchMapping("/{id}/finish")
    public ResponseEntity<StudySessionResponse> finish(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal
            ) {
        return ResponseEntity.ok(
                studySessionService.finish(id, principal.getUsername())
        );
    }

    @GetMapping
    public ResponseEntity<List<StudySessionResponse>> list(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal
    ) {

        return ResponseEntity.ok(
                studySessionService.listMySessions(principal.getUsername())
        );
    }
}
