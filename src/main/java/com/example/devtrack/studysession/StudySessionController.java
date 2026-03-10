package com.example.devtrack.studysession;


import com.example.devtrack.studysession.dto.CreateStudySessionRequest;
import com.example.devtrack.studysession.dto.StudySessionResponse;

import com.example.devtrack.studysession.dto.UpdateSessionRequest;
import jakarta.validation.Valid;
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
            @Valid @RequestBody CreateStudySessionRequest request,
            @AuthenticationPrincipal UserDetails principal
            ) {

        StudySessionResponse response = studySessionService.create(
                request, principal.getUsername()
        );

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<StudySessionResponse>> list(
            @AuthenticationPrincipal UserDetails principal
    ) {

        return ResponseEntity.ok(
                studySessionService.listMySessions(principal.getUsername())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudySessionResponse> getSession(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal
            ) {
        return ResponseEntity.ok(
                studySessionService.getSession(id, principal.getUsername())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudySessionResponse> updateSession(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal,
            @Valid @RequestBody UpdateSessionRequest request
            ) {
        return ResponseEntity.ok(
                studySessionService.updateSession(request, principal.getUsername(), id)
        );
    }

    @PatchMapping("/{id}/finish")
    public ResponseEntity<StudySessionResponse> finish(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal
            ) {
        StudySessionResponse response = studySessionService.finish(id, principal.getUsername());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal
    ) {
        studySessionService.deleteSession(id, principal.getUsername());
        return ResponseEntity.noContent().build();
    }
}
