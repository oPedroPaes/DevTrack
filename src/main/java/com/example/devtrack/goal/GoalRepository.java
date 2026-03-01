package com.example.devtrack.goal;
import com.example.devtrack.user.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GoalRepository extends JpaRepository<Goal, UUID> {

    List<Goal> findByUser(User user);

    Optional<Goal> findByIdAndUser(UUID id, User user);
}
