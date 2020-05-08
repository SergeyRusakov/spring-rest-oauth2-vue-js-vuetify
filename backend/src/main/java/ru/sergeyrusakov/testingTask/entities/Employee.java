package ru.sergeyrusakov.testingTask.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import ru.sergeyrusakov.testingTask.validation.annotations.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "employees")
public class Employee implements Serializable {
    public Employee() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NonNull
    @Size(min = 1,max = 15)
    @EmployeeNameValidationConstraint
    private String name;

    @Column
    @NonNull
    @Size(min = 1,max = 15)
    @EmployeeSurnameValidationConstraint
    private String surname;

    @Column(name = "birth_date")
    @NonNull
    @EmployeeBirthDateValidationConstraint
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column
    @NonNull
    @EmailValidationConstraint
    @Size(min = 5)
    private String email;

    @Column(name = "phone_number")
    @PhoneNumberValidationConstraint
    private String phoneNumber;

    @Column(name = "is_working")
    @JsonProperty
    private boolean isWorking;

    @Column(updatable = false, name = "time_added")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @NonNull
    private LocalDateTime timeAdded;

    @Column(name="time_updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @NonNull
    private LocalDateTime timeUpdated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id")
    private Position position;
}
