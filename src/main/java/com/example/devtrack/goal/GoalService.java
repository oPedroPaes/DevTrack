package com.example.devtrack.goal;

import com.example.devtrack.goal.dto.CreateGoalRequest;
import com.example.devtrack.goal.dto.GoalResponse;
import com.example.devtrack.user.User;
import com.example.devtrack.user.UserRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public GoalResponse createGoal(CreateGoalRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Goal goal = new Goal();
        goal.setTitle(request.title());
        goal.setDescription(request.description());
        goal.setTargetDate(request.targetDate());
        goal.setStatus(Goal.GoalStatus.ATIVO);
        goal.setUser(user);

        Goal saved = goalRepository.save(goal);

        return new GoalResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getTargetDate(),
                saved.getStatus()
        );
    }

    public List<GoalResponse> listMyGoals(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return goalRepository.findByUser(user)
                .stream()
                .map(goal -> new GoalResponse(
                        goal.getId(),
                        goal.getTitle(),
                        goal.getDescription(),
                        goal.getTargetDate(),
                        goal.getStatus()
                ))
                .toList();
    }
}
