package com.example.devtrack.goal;

import com.example.devtrack.goal.dto.CreateGoalRequest;

import com.example.devtrack.goal.dto.GoalResponse;
import com.example.devtrack.goal.dto.UpdateGoalRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<GoalResponse> create(
            @Valid @RequestBody CreateGoalRequest request,
            @AuthenticationPrincipal UserDetails principal
    ) {

        GoalResponse response = goalService.createGoal(request, principal.getUsername());

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GoalResponse>> list(
            @AuthenticationPrincipal UserDetails principal
    ) {

        return ResponseEntity.ok(goalService.listMyGoals(principal.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResponse> getGoal(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal
    ) {
        return ResponseEntity.ok(
                goalService.getGoal(id, principal.getUsername())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalResponse> updateGoal(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails principal,
            @Valid @RequestBody UpdateGoalRequest request
            ) {
        return ResponseEntity.ok(
                goalService.updateGoal(request, principal.getUsername(), id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(
        @PathVariable UUID id,
        @AuthenticationPrincipal UserDetails principal
    ) {
        goalService.deleteGoal(id, principal.getUsername());
        return ResponseEntity.noContent().build();
    }
}
