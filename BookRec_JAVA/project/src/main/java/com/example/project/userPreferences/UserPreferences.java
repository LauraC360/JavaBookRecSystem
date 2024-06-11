package com.example.project.userPreferences;

import com.example.project.authentication.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user_preferences")
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection
    private List<String> preferredGenres;
}