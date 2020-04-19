package ru.sergeyrusakov.testingTask.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import ru.sergeyrusakov.testingTask.validation.annotations.EmployeeBirthDateValidationConstraint;
import ru.sergeyrusakov.testingTask.validation.annotations.EmployeeEmailValidationConstraint;
import ru.sergeyrusakov.testingTask.validation.annotations.EmployeeNameValidationConstraint;
import ru.sergeyrusakov.testingTask.validation.annotations.EmployeeSurnameValidationConstraint;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
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
    @EmployeeEmailValidationConstraint
    @Size(min = 5)
    private String email;

    @Column(name = "is_married")
    @JsonProperty
    private boolean isMarried;

    @Column(updatable = false, name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @NonNull
    private LocalDateTime creationDate;

    @Column(name="time_last_edited")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @NonNull
    private LocalDateTime timeLastEdited;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getTimeLastEdited() {
        return timeLastEdited;
    }

    public void setTimeLastEdited(LocalDateTime timeLastEdited) {
        this.timeLastEdited = timeLastEdited;
    }
}
