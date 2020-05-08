package ru.sergeyrusakov.testingTask.validation.annotations;

import ru.sergeyrusakov.testingTask.validation.validators.EmployeeNameValidator;
import ru.sergeyrusakov.testingTask.validation.validators.PositionNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= PositionNameValidator.class)
public @interface PositionNameValidationConstraint {
    String message() default "Invalid position name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
