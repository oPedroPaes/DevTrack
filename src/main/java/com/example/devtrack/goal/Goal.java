package com.example.devtrack.goal;

import com.example.devtrack.studysession.StudySession;
import com.example.devtrack.user.User;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    private LocalDate targetDate;

    @Enumerated(EnumType.STRING)
    private GoalStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "FK_GOAL_USER", value = ConstraintMode.CONSTRAINT)
                )
                private User user;

    @OneToMany(mappedBy = "goal")
    private List<StudySession> sessions = new ArrayList<>();

    public enum GoalStatus {
        ATIVO,
        COMPLETO,
        CANCELADO
    }
}
