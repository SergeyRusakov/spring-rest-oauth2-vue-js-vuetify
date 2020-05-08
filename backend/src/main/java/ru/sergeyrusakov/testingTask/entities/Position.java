package ru.sergeyrusakov.testingTask.entities;

import lombok.Data;
import ru.sergeyrusakov.testingTask.validation.annotations.PositionNameValidationConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "positions")
@Data
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @PositionNameValidationConstraint
    @Size(min = 3)
    @Column(unique = true)
    private String name;
}
