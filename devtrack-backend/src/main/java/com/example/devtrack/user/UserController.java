package com.example.devtrack.user;

import com.example.devtrack.goal.GoalService;
import com.example.devtrack.goal.dto.GoalResponse;
import com.example.devtrack.studysession.StudySessionService;
import com.example.devtrack.studysession.dto.StudySessionResponse;
import com.example.devtrack.user.dto.UpdateUserRequest;
import com.example.devtrack.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final GoalService goalService;
    private final StudySessionService sessionService;

    // GET /users/me
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @AuthenticationPrincipal UserDetails principal
            ) {
        return ResponseEntity.ok(
                userService.getUserByEmail(principal.getUsername())
        );
    }

    // PUT /users/me
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrentUser(
            @AuthenticationPrincipal UserDetails principal,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return ResponseEntity.ok(
                userService.updateUser(request, principal.getUsername())
        );
    }

    // DELETE /users/me
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentUser(
            @AuthenticationPrincipal UserDetails principal
    ) {
        userService.deleteUser(principal.getUsername());
        return ResponseEntity.noContent().build();
    }

    // GET /users/me/goals
    @GetMapping("/me/goals")
    public ResponseEntity<List<GoalResponse>> getMyGoals(
            @AuthenticationPrincipal UserDetails principal
    ) {
        return ResponseEntity.ok(
                goalService.listMyGoals(principal.getUsername())
        );
    }

    // GET /users/me/sessions
    @GetMapping("/me/sessions")
    public ResponseEntity<List<StudySessionResponse>> getMySessions(
            @AuthenticationPrincipal UserDetails principal
    ) {
        return ResponseEntity.ok(
                sessionService.listMySessions(principal.getUsername())
        );
    }
}
