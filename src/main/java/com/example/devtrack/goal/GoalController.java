package com.example.devtrack.goal;

import com.example.devtrack.goal.dto.CreateGoalRequest;

import com.example.devtrack.goal.dto.GoalResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<GoalResponse> create(
            @Valid @RequestBody CreateGoalRequest request,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal
    ) {

        GoalResponse response = goalService.createGoal(request, principal.getUsername());

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GoalResponse>> list(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal
    ) {

        return ResponseEntity.ok(goalService.listMyGoals(principal.getUsername()));
    }
}
