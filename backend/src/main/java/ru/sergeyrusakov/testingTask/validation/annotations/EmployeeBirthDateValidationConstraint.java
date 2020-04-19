package ru.sergeyrusakov.testingTask.validation.annotations;

import ru.sergeyrusakov.testingTask.validation.validators.EmployeeBirthDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= EmployeeBirthDateValidator.class)
public @interface EmployeeBirthDateValidationConstraint {
    String message() default "Invalid birth date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
