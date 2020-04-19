package ru.sergeyrusakov.testingTask.validation.annotations;

import ru.sergeyrusakov.testingTask.validation.validators.EmployeeSurnameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= EmployeeSurnameValidator.class)
public @interface EmployeeSurnameValidationConstraint {
    String message() default "Invalid surname";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}