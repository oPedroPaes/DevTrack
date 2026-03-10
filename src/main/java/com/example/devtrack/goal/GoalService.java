package com.example.devtrack.goal;

import com.example.devtrack.exception.GoalNotFoundException;
import com.example.devtrack.exception.UserNotFoundException;
import com.example.devtrack.goal.dto.CreateGoalRequest;
import com.example.devtrack.goal.dto.GoalResponse;
import com.example.devtrack.goal.dto.UpdateGoalRequest;
import com.example.devtrack.user.User;
import com.example.devtrack.user.UserRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public GoalResponse createGoal(CreateGoalRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

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

    public GoalResponse getGoal(UUID id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Goal goal = goalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new GoalNotFoundException("Goal not found"));
        return mapToResponse(goal);
    }

    public List<GoalResponse> listMyGoals(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

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

    public GoalResponse updateGoal(UpdateGoalRequest request, String email, UUID id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Goal goal = goalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new GoalNotFoundException("Goal not found"));

        goal.setTitle(request.title());
        goal.setDescription(request.description());

        Goal saved = goalRepository.save(goal);
        return mapToResponse(saved);
    }

    public void deleteGoal(UUID id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Goal goal = goalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new GoalNotFoundException("Goal not found"));
        goalRepository.delete(goal);
    }

    private GoalResponse mapToResponse(Goal goal) {
        return new GoalResponse(
                goal.getId(),
                goal.getTitle(),
                goal.getDescription(),
                goal.getTargetDate(),
                goal.getStatus()
        );
    }
}
