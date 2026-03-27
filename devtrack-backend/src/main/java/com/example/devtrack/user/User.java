package com.example.devtrack.user;

import com.example.devtrack.goal.Goal;

import com.example.devtrack.studysession.StudySession;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public enum UserRole {
        ROLE_USER,
        ROLE_ADMIN
    }

    @OneToMany(mappedBy = "user")
    private List<Goal> goals = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<StudySession> sessions = new ArrayList<>();

}
