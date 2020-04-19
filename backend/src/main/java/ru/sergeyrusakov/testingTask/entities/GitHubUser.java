package ru.sergeyrusakov.testingTask.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="github_users")
public class GitHubUser {
    @Id
    private int id;
    private String name;
    private String login;
    @Column(name = "last_visit")
    private LocalDateTime lastVisit;
    private String role;
}
