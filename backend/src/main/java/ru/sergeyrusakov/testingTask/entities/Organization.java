package ru.sergeyrusakov.testingTask.entities;

import lombok.Data;
import ru.sergeyrusakov.testingTask.validation.annotations.EmailValidationConstraint;
import ru.sergeyrusakov.testingTask.validation.annotations.PhoneNumberValidationConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name="organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 3)
    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Size(min = 3)
    private String country;

    @NotNull
    @Size(min = 3)
    private String city;

    @NotNull
    @Size(min = 3)
    private String street;

    private String building;

    @Column(name = "phone_number")
    @PhoneNumberValidationConstraint
    @NotNull
    private String phoneNumber;

    @NotNull
    @EmailValidationConstraint
    @Size(min = 5)
    private String email;

    @Column(name = "time_added")
    private LocalDateTime timeAdded;

    @Column(name = "time_updated")
    private LocalDateTime timeUpdated;
}
