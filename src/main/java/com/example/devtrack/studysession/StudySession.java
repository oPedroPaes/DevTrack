package com.example.devtrack.studysession;

import com.example.devtrack.goal.Goal;
import com.example.devtrack.user.User;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "study_sessions")
public class StudySession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate date;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer durationInMinutes;

    private String subject;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "FK_SESSION_USER", value = ConstraintMode.CONSTRAINT)
                )
                private User user;

    @ManyToOne
    @JoinColumn(name = "goal_id",
            foreignKey = @ForeignKey(name = " FK_SESSION_GOAL", value = ConstraintMode.CONSTRAINT)
                )
                private Goal goal;

}
